package ngocnth.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ngocnth.account.AccountDTO;
import ngocnth.article.ArticleDAO;
import ngocnth.article.ArticleDTO;
import ngocnth.util.RandomStringUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

@WebServlet(name = "PostArticleServlet", urlPatterns = {"/PostArticleServlet"})
public class PostArticleServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(PostArticleServlet.class);

    private static final String MY_WALL_PAGE = "myWall.jsp";
    private static final String ERROR_PAGE = "error.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String url = ERROR_PAGE;
        try {
            boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
            //System.out.println("PostArticleServlet: isMultiPart " + isMultiPart);
            if (isMultiPart) { //get toan bo du lieu va dua thanh list item

                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = null;

                try {
                    items = upload.parseRequest(request);
                } catch (FileUploadException ex) {
                    LOGGER.info("FileUploadException " + ex.getMessage());
                }

                Iterator iter = items.iterator();
                Hashtable params = new Hashtable(); //khai bao hashtable de lay cac tham so truyen qua control tren form...
                String filename = null;

                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if (item.isFormField()) //... ngoai tru file
                    {
                        params.put(item.getFieldName(), item.getString());
                    } else {
                        try { //lay ten file, tao duong dan va luu vao thu muc va thuc hien ghi thanh file
                            String itemName = item.getName();
                            filename = itemName.substring(itemName.lastIndexOf("\\") + 1);
                            //System.out.println("Path: " + filename);
                            if (!filename.trim().equals("")) { //neu co anh thi luu lai
                                String realPath = getServletContext().getRealPath("/")
                                        + "img\\" + filename;
                                //System.out.println("Real path: " + realPath);
                                File savedFile = new File(realPath);
                                item.write(savedFile);
                            }

                        } catch (Exception ex) {
                            LOGGER.info("Exception " + ex.getMessage());
                        }
                    }
                } //end while

                //get value and save in DB
                AccountDTO dto = null;
                HttpSession session = request.getSession(false);
                if (session != null) {
                    dto = (AccountDTO) session.getAttribute("ACCOUNT");
                    String email = dto.getEmail();
                    String title = (String) params.get("txtTitle");
                    String description = (String) params.get("txtDescription");
                    String image;
                    if (filename.trim().equals(""))
                        image = "";
                    else 
                        image = "img/" + filename;
                    Date date = new Date();
                    int statusId = 1;
                    String postId;

                    ArticleDAO dao = new ArticleDAO();

                    do {
                        postId = RandomStringUtils.randomAlphanummeric(9);
                    } while (dao.isExistedPostId(postId));

                    boolean result = dao.postArticle(postId, date, title, image, description, email, statusId);
                    if (result) {
                        List<ArticleDTO> list = dao.searchArticleByEmail(email, 0, 20);
                        session.setAttribute("LIST_ARTICLE_BY_MAIL", list);
                        url = MY_WALL_PAGE;
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            LOGGER.info("SQLException " + ex.getMessage());
        } catch (NamingException ex) {
            System.out.println(ex.getMessage());
            LOGGER.info("NamingException " + ex.getMessage());
        } catch (Exception ex) {
            LOGGER.info("PostArticleServlet: " + ex.getMessage());
            //System.err.println(ex);
        } finally {
            response.sendRedirect(url);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

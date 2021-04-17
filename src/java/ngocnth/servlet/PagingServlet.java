package ngocnth.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ngocnth.article.ArticleDAO;
import ngocnth.article.ArticleDTO;
import org.apache.log4j.Logger;

@WebServlet(name = "PagingServlet", urlPatterns = {"/PagingServlet"})
public class PagingServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(PagingServlet.class);
    
    private static final String HOME_PAGE = "homepage.jsp";
    private static final String MY_WALL_PAGE = "myWall.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        int recordPerPage = 20;
        String checkPage = request.getParameter("checkPage");
        String url = "";
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                ArticleDAO dao = new ArticleDAO();
                int page = Integer.parseInt(request.getParameter("pageNumber"));
                //int totalPage = Integer.parseInt(request.getParameter("totalPage"));
                int skipRows = (page - 1) * recordPerPage;
                int nextRows = recordPerPage;

                if (checkPage.equals("isHomepage")) {
                    String searchValue = request.getParameter("txtSearchValue");
                    List<ArticleDTO> list = dao.searchArticle(searchValue, skipRows, nextRows);
                    if (list != null) {
                        session.setAttribute("LIST_ARTICLE", list);
                        url = HOME_PAGE;
                    }
                }
                
                if (checkPage.equals("isMyWall")) {
                    String email = request.getParameter("txtEmail");
                    List<ArticleDTO> list = dao.searchArticleByEmail(email, skipRows, nextRows);
                    if (list != null) {
                        session.setAttribute("LIST_ARTICLE_BY_MAIL", list);
                        url = MY_WALL_PAGE;
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.info("SQLException " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.info("NamingException " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            //response.sendRedirect(url);
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

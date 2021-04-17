package ngocnth.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ngocnth.article.ArticleDAO;
import ngocnth.article.ArticleDTO;
import org.apache.log4j.Logger;

@WebServlet(name = "DeleteArticleServlet", urlPatterns = {"/DeleteArticleServlet"})
public class DeleteArticleServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(DeleteArticleServlet.class);

    private static final String ERROR_PAGE = "error.html";
    private static final String MY_WALL_PAGE = "myWall.jsp";
    //private static final String HOME_PAGE = "homepage.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        int recordPerPage = 20;
        String postId = request.getParameter("txtPostId");
        String checkPage = request.getParameter("checkPage");
        String url = ERROR_PAGE;

        try {
            ArticleDAO dao = new ArticleDAO();
            boolean result = dao.deleteArticle(postId);

            if (result) {
                HttpSession session = request.getSession(false);

                if (session != null) {

                    if (checkPage.equals("isMyWall")) {
                        String email = request.getParameter("txtEmail");
                        List<ArticleDTO> listByMail = dao.searchArticleByEmail(email, 0, recordPerPage);
                        session.setAttribute("LIST_ARTICLE_BY_MAIL", listByMail);
                        url = MY_WALL_PAGE;
                    }
                    if (checkPage.equals("isHomepage")) {
                        String searchValue = request.getParameter("txtSearchValue");
                        url = "DispatchController?"
                                + "btnAction=Search"
                                + "&txtSearchValue=" + searchValue;
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.info("SQLException " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.info("NamingException " + ex.getMessage());
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

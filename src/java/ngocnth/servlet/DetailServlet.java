
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
import ngocnth.comment.CommentDAO;
import ngocnth.comment.CommentDTO;
import ngocnth.emotion.EmotionDAO;
import ngocnth.emotion.EmotionDTO;
import org.apache.log4j.Logger;

@WebServlet(name = "DetailServlet", urlPatterns = {"/DetailServlet"})
public class DetailServlet extends HttpServlet {
    
    private static final Logger LOGGER = Logger.getLogger(DetailServlet.class);
    
    private static final String ARTICLE_DETAIL_PAGE = "articleDetail.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String postId = request.getParameter("txtPostId");
        String url = ARTICLE_DETAIL_PAGE;
        
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                ArticleDAO dao = new ArticleDAO();
                ArticleDTO dto = dao.getArticle(postId);
                if (dto != null)
                    session.setAttribute("ARTICLE_DETAIL", dto);
                else
                    session.removeAttribute("ARTICLE_DETAIL");
                
                CommentDAO cDao = new CommentDAO();
                List<CommentDTO> listCmt = cDao.getCmtsByPostId(postId);
                if (listCmt != null)
                    session.setAttribute("LIST_CMT", listCmt);
                else
                    session.removeAttribute("LIST_CMT");
                
                EmotionDAO eDao = new EmotionDAO();
                List<EmotionDTO> listEmo = eDao.getEmotionsByPostId(postId);
                if (listEmo != null)
                    session.setAttribute("LIST_EMO", listEmo);
                else
                    session.removeAttribute("LIST_EMO");
            }
        } catch (NamingException ex) {
            LOGGER.info("NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            LOGGER.info("SQLException: " + ex.getMessage());
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

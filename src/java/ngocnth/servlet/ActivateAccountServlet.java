
package ngocnth.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ngocnth.account.AccountDAO;
import org.apache.log4j.Logger;

@WebServlet(name = "ActivateAccountServlet", urlPatterns = {"/ActivateAccountServlet"})
public class ActivateAccountServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ActivateAccountServlet.class);
    private static final String INVALID_CODE_PAGE = "invalidCode.html";
    private static final String LOGIN_PAGE = "login.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String verifyCode = request.getParameter("txtCode");
        String url = INVALID_CODE_PAGE;
        
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                String email = (String) session.getAttribute("EMAIL");
                String code = (String) session.getAttribute("CODE");

                if (verifyCode.equals(code)) {
                    AccountDAO dao = new AccountDAO();
                    boolean result = dao.updateActiveAccount(email, verifyCode);
                    
                    if (result) {
                        session.removeAttribute("CODE");
                        session.removeAttribute("EMAIL");
                        url = LOGIN_PAGE;
                    }
                }
            }

        } catch (NamingException ex) {
            LOGGER.info("ActivateAccountServlet_NamingException " + ex.getMessage());
        } catch (SQLException ex) {
            LOGGER.info("ActivateAccountServlet_SQLException " + ex.getMessage());
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

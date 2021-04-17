package ngocnth.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Random;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ngocnth.account.AccountDAO;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

@WebServlet(name = "SignupServlet", urlPatterns = {"/SignupServlet"})
public class SignupServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SignupServlet.class);
    
    private static final String VERIFICATION_PAGE = "verification.html";
    private static final String SIGNUP_PAGE = "signup.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        //String confirmPassword = request.getParameter("txtConfirmPassword");
        String name = request.getParameter("txtName");
        String encryptPassword = DigestUtils.sha256Hex(password);
        String url = SIGNUP_PAGE;

        try {
            AccountDAO dao = new AccountDAO();
            HttpSession session = request.getSession();
            
            if (!dao.duplicatedEmail(email)) {
                session.removeAttribute("ERROR");
                
                Random random = new Random();
                String verifyCode = "" + random.nextInt(999999);
                
                boolean result = dao.createNewAccount(email, encryptPassword, name, verifyCode);
                if (result) {
                    session.setAttribute("EMAIL", email);
                    session.setAttribute("CODE", verifyCode);
                    url = VERIFICATION_PAGE;
                }
            } else {
                String errMsg = "This email acccount has been used";
                session.setAttribute("ERROR", errMsg);
            }
            
        } catch (SQLException ex) {
            LOGGER.info("SignupServlet_SQLException " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.info("SignupServlet_NamingException " + ex.getMessage());
        } catch (MessagingException ex) {
            LOGGER.info("SignupServlet_MessagingException " + ex.getMessage());
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

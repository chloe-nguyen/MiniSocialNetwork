
package ngocnth.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet(name = "DispatchController", urlPatterns = {"/DispatchController"})
public class DispatchController extends HttpServlet {
    
    private static final Logger LOGGER = Logger.getLogger(PostArticleServlet.class);
    
    private static final String LOGIN_PAGE = "login.html";
    private static final String UNAVAILABLE_PAGE = "unavailable.html";
    private static final String HOMEPAGE = "homepage.jsp";
    private static final String SIGNUP_PAGE = "signup.jsp";
    private static final String VERIFICATION_PAGE = "verification.html";
    
    private static final String SIGNUP_CONTROLLER = "SignupServlet";
    private static final String LOGIN_CONTROLLER = "LoginServlet";
    private static final String LOGOUT_CONTROLLER = "LogoutServlet";
    private static final String ACTIVATE_ACCOUNT_CONTROLLER = "ActivateAccountServlet";
    private static final String SEARCH_ARTICLE_CONTROLLER = "SearchArticleServlet";
    private static final String POST_ARTICLE_CONTROLLER = "PostArticleServlet";
    private static final String DELETE_ARTICLE_CONTROLLER = "DeleteArticleServlet";
    private static final String MY_WALL_CONTROLLER = "MyWallServlet";
    private static final String HOME_PAGE_CONTROLLER = "HomepageServlet";
    private static final String PAGING_CONTROLLER = "PagingServlet";
    private static final String DETAIL_CONTROLLER = "DetailServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String button = request.getParameter("btnAction");
        String url = "";
        try {
            if (button == null)
                url = LOGIN_PAGE;
            else if (button.equals("Login"))
                url = LOGIN_CONTROLLER;
            else if (button.equals("Sign up"))
                url = SIGNUP_CONTROLLER;
            else if (button.equals("Log out"))
                url = LOGOUT_CONTROLLER;
            else if (button.equals("Verify"))
                url = ACTIVATE_ACCOUNT_CONTROLLER;
            else if (button.equals("Search"))
                url = SEARCH_ARTICLE_CONTROLLER;
            else if (button.equals("Post"))
                url = POST_ARTICLE_CONTROLLER;
            else if (button.equals("Homepage"))
                url = HOME_PAGE_CONTROLLER;
            else if (button.equals("My Wall"))
                url = MY_WALL_CONTROLLER;
            else if (button.equals("Pagination"))
                url = PAGING_CONTROLLER;
            else if (button.equals("Detail"))
                url = DETAIL_CONTROLLER;
            else if (button.equals("Delete"))
                url = DELETE_ARTICLE_CONTROLLER;
            else if (button.equals("homepage"))
                url = HOMEPAGE;
            else if (button.equals("loginPage"))
                url = LOGIN_PAGE;
            else if (button.equals("signupPage"))
                url = SIGNUP_PAGE;
            else if (button.equals("verificationPage"))
                url = VERIFICATION_PAGE;
            
            else
                url = UNAVAILABLE_PAGE;
        } catch(Exception ex) {
            LOGGER.info("Exception: " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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

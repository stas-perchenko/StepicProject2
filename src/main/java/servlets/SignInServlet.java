package servlets;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {
    private final AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        final String login = req.getParameter("login");
        final String passw = req.getParameter("password");

        response.setContentType("text/html;charset=utf-8");
        if (login == null || login.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Login not provided");
        } else if (passw == null || passw.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Password not provided");
        } else {
            UserProfile usr = accountService.getUserByLogin(login);
            if ((usr != null) && passw.equals(usr.getPass())) {
                accountService.addSession(req.getSession().getId(), usr);
                response.getWriter().println(String.format("Authorized: %s", login));
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.getWriter().println("Unau__thorized_fjhsdgfjsf");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        }
    }
}

package servlets;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
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
        } else if (accountService.getUserByLogin(login) != null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, String.format("User %s already registered", login));
        } else {
            accountService.addNewUser(new UserProfile(login, passw, ""));
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}

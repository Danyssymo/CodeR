package uno.dos.tres.controller.concrete.impl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uno.dos.tres.bean.AuthInfo;
import uno.dos.tres.bean.User;
import uno.dos.tres.controller.concrete.Command;
import uno.dos.tres.service.ServiceException;
import uno.dos.tres.service.ServiceProvider;
import uno.dos.tres.service.UserService;

import java.io.IOException;
import java.net.URLEncoder;

public class DoAuth implements Command {

    private final UserService userSerivice = ServiceProvider.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String mail = request.getParameter("login");
        String password = request.getParameter("password");
        User user = null;
        try {
            user = userSerivice.signIn(new AuthInfo(mail, password));
            if (user != null) {
                HttpSession session = (HttpSession) request.getSession(true);
                session.setAttribute("user", user);

                String rememberMe = request.getParameter("remember-me");
                if (rememberMe != null) {
                    Cookie cookie = new Cookie("remember-me", "user-token123");
                    cookie.setPath("/");
                    cookie.setHttpOnly(true);
                    cookie.setSecure(true);
                    response.addCookie(cookie);
                }
                response.sendRedirect("MyController?command=go_to_main_page");
            } else {
                response.sendRedirect("MyController?command=go_to_welcome_page&Info=Wrong login or password!");
            }
        } catch (ServiceException e) {
            String errorMessage = URLEncoder.encode(e.getMessage(), "UTF-8");
            response.sendRedirect("MyController?command=go_to_welcome_page&Info=" + errorMessage);
        }

    }
}
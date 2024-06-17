package uno.dos.tres.controller.concrete.impl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uno.dos.tres.bean.RegInfo;
import uno.dos.tres.bean.UserRoles;
import uno.dos.tres.controller.concrete.Command;
import uno.dos.tres.service.ServiceException;
import uno.dos.tres.service.ServiceProvider;
import uno.dos.tres.service.UserService;

import java.io.IOException;
import java.net.URLEncoder;

public class DoRegistration implements Command {

    private final UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String mail = request.getParameter("mail");
        String role = request.getParameter("creator");
        RegInfo regInfo = new RegInfo();
        regInfo.setUserName(username);
        regInfo.setPassword(password);
        regInfo.setEmail(mail);
        if (role != null) {
            regInfo.setRole(UserRoles.CREATOR);
        } else {
            regInfo.setRole(UserRoles.USER);
        }
        try {
            userService.signUp(regInfo);
            response.sendRedirect("MyController?command=go_to_welcome_page&Info=Success registration");
        } catch (ServiceException e) {
            String errorMessage = URLEncoder.encode(e.getMessage(), "UTF-8");
            response.sendRedirect("MyController?command=go_to_welcome_page&Info=" + errorMessage);
        }

    }
}

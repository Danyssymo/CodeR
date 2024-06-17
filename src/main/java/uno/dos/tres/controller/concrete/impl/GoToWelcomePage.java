package uno.dos.tres.controller.concrete.impl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uno.dos.tres.controller.concrete.Command;

import java.io.IOException;

public class GoToWelcomePage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = (HttpSession) request.getSession(false);
        if (session != null) {
            session.setAttribute("lastPage", "go_to_welcome_page");
        }
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/welcome.jsp");
        rd.forward(request, response);
    }
}

package uno.dos.tres.controller.concrete.impl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uno.dos.tres.controller.concrete.Command;

import java.io.IOException;

public class ChangeLocal implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String local = request.getParameter("lang");
        HttpSession session = (HttpSession) request.getSession(true);
        session.setAttribute("local", local);
        System.out.println("local in CL: " + local);
        String lastPage = (String) session.getAttribute("lastPage");
        response.sendRedirect("MyController?command=" + lastPage);
    }
}

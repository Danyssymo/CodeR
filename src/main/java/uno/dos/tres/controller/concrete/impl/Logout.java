package uno.dos.tres.controller.concrete.impl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uno.dos.tres.controller.concrete.Command;

import java.io.IOException;

public class Logout implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("user");
            //session.invalidate();
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("remember-me")) {
                    cookie.setValue(""); // Устанавливаем значение в пустую строку
                    cookie.setPath("/"); // Убедитесь, что путь соответствует пути устанавливаемого cookie
                    cookie.setMaxAge(0); // Устанавливаем время жизни в 0, чтобы удалить cookie
                    response.addCookie(cookie); // Добавляем модифицированный cookie в ответ
                }
            }
        }

        response.sendRedirect("MyController?command=go_to_welcome_page&Info=Success logout");

    }
}

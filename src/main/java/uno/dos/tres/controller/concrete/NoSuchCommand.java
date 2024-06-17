package uno.dos.tres.controller.concrete;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class NoSuchCommand implements Command{
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.getWriter().print("Нет такой команды!");
    }
}

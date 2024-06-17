package uno.dos.tres.controller.concrete.impl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uno.dos.tres.bean.News;
import uno.dos.tres.bean.User;
import uno.dos.tres.controller.concrete.Command;
import uno.dos.tres.service.NewsService;
import uno.dos.tres.service.ServiceException;
import uno.dos.tres.service.ServiceProvider;
import uno.dos.tres.service.UserService;

import java.io.IOException;

public class AddNews implements Command {

    private final NewsService newsSerivice = ServiceProvider.getInstance().getNewsService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String path = request.getParameter("image");
        String imgPath = "images/" + path;
        HttpSession session = (HttpSession) request.getSession(false);
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();
        News news = new News(title, content, imgPath);
        try {
            newsSerivice.addNews(news, username);
            response.sendRedirect("MyController?command=go_to_main_page&Info1=Successful addition");
        } catch (ServiceException e) {
            response.sendRedirect("MyController?command=go_to_main_page&Info1=Unsuccessful addition");
        }

    }
}

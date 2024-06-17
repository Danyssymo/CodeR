package uno.dos.tres.controller.concrete.impl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uno.dos.tres.bean.News;
import uno.dos.tres.controller.concrete.Command;
import uno.dos.tres.service.NewsService;
import uno.dos.tres.service.ServiceException;
import uno.dos.tres.service.ServiceProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoToMainPage implements Command {

    private final NewsService newsService = ServiceProvider.getInstance().getNewsService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = (HttpSession) request.getSession(false);
        session.setAttribute("lastPage", "go_to_main_page");
        List<String> images = Arrays.asList("camping-1.jpg", "camping-2.jpg", "camping-3.jpg");
        request.setAttribute("images", images);
        if (session != null && session.getAttribute("user") != null) {
            List<News> myNews;
            try {
                myNews = newsService.getNews();
                if (myNews.isEmpty()) {
                    myNews.add(new News("There is no news yet", "Perhaps they will appear later", "images/no_news.jpg"));
                }
            } catch (ServiceException e) {
                myNews = new ArrayList<>();
                myNews.add(new News("ERROR", "Perhaps they will appear later", "images/no_news.jpg"));
            }
            request.setAttribute("myNews", myNews);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
            rd.forward(request, response);
        } else {
            response.sendRedirect("MyController?command=go_to_welcome_page&Info=Need to log in");
        }
    }
}

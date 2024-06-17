package uno.dos.tres.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uno.dos.tres.bean.User;
import uno.dos.tres.service.ServiceException;
import uno.dos.tres.service.ServiceProvider;
import uno.dos.tres.service.UserService;

import java.io.IOException;

public class RememberMeFilter extends HttpFilter implements Filter {

    private final UserService userService = ServiceProvider.getInstance().getUserService();

    public RememberMeFilter() {
        super();
    }

    public void destroy() {
    }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpSession session = ((HttpServletRequest) request).getSession(false);
            if (session != null) {
                if (session.getAttribute("user") == null) {
                    Cookie[] cookies = ((HttpServletRequest) request).getCookies();
                    if (cookies != null) {
                        for (Cookie c : cookies) {
                            if (c.getName().equals("remember-me")) {
                                String token = c.getValue();
                                User user = userService.rememberMe(token);
                                session.setAttribute("user", user);
                                System.out.println("4");
                            }
                        }
                    }
                }
            }

            chain.doFilter(request, response);
        } catch (ServiceException e) {
            ((HttpServletResponse) response).sendRedirect("MyController?command=go_to_welcome_page&Info=Wrong auth");
        }
    }
    public void init(FilterConfig fConfig) throws ServletException {

    }


}

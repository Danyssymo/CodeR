package uno.dos.tres.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpFilter;

import java.io.IOException;

public class EncodingFilter extends HttpFilter implements Filter {

    public EncodingFilter() {
        super();
    }

    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }


    public void init(FilterConfig fConfig) throws ServletException {
    }



}

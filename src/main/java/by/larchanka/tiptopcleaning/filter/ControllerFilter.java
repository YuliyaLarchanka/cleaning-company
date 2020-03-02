package by.larchanka.tiptopcleaning.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.larchanka.tiptopcleaning.util.CommonConstant.EMPTY;
import static by.larchanka.tiptopcleaning.util.CommonConstant.URL_SEPARATOR;

public class ControllerFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String method = request.getMethod();
        String uri = request.getServletPath().replace(URL_SEPARATOR, EMPTY);

        if (method.equals("GET") && uri.equalsIgnoreCase("controller")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }
}
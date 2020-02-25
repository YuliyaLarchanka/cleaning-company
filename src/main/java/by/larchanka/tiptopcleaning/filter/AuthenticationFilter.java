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

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.USER_ID;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_HOME;

public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getSession().getAttribute(USER_ID) != null) {
            response.sendRedirect(PATH_HOME);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}

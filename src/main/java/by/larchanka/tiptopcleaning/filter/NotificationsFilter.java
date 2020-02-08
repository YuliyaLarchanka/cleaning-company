package by.larchanka.tiptopcleaning.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.larchanka.tiptopcleaning.util.CommonConstant.ERROR_KEY;
import static by.larchanka.tiptopcleaning.util.CommonConstant.SUCCESS_KEY;

public class NotificationsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() { }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();


        Object attribute = session.getAttribute(ERROR_KEY);
        if (attribute != null) {
            request.setAttribute(ERROR_KEY, attribute);
            session.removeAttribute(ERROR_KEY);
        }

        attribute = session.getAttribute(SUCCESS_KEY);
        if (attribute != null) {
            request.setAttribute(SUCCESS_KEY, attribute);
            session.removeAttribute(SUCCESS_KEY);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}

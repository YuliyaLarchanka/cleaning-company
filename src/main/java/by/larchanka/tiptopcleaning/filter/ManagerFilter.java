package by.larchanka.tiptopcleaning.filter;

import by.larchanka.tiptopcleaning.entity.UserType;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.ACCOUNT_TYPE;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_ERROR;

public class ManagerFilter implements Filter {

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

        UserType userType = (UserType) request.getSession().getAttribute(ACCOUNT_TYPE);

        if (userType != UserType.MANAGER) {
            response.sendRedirect(PATH_ERROR);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }
}

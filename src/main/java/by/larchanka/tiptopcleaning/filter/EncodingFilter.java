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

import static by.larchanka.tiptopcleaning.util.CommonConstant.CONTENT_TYPE_TEXT_HTML;
import static by.larchanka.tiptopcleaning.util.CommonConstant.ENCODING_UTF_8;

public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() { }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        request.setCharacterEncoding(ENCODING_UTF_8);
        response.setContentType(CONTENT_TYPE_TEXT_HTML);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}

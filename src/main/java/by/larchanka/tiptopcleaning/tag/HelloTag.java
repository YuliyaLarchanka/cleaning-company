package by.larchanka.tiptopcleaning.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class HelloTag extends TagSupport {
    private String role;

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            String to = null;
            if ("admin".equalsIgnoreCase(role)) {
                to = "Hello, " + role;
            } else {
                to = "Welcome, " + role;
            }
            pageContext.getOut().write("<h5>" + to + "<h5/>");
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}

package org.example.mvc.view;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JspView implements View {

    private final String name;

    public JspView(String name) {
        this.name = name;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        model.forEach(httpServletRequest::setAttribute);
        RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(name);
        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }
}

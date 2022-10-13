package org.example.mvc;

import org.example.mvc.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SimpleControllerHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean isSupport(Object handler) {
        return (handler instanceof Controller);
    }

    @Override
    public ModelAndView handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {

        String viewName = ((Controller) handler).handleRequest(httpServletRequest, httpServletResponse);
        return new ModelAndView(viewName);
    }

}

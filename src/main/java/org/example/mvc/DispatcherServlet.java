package org.example.mvc;

import org.example.mvc.controller.Controller;
import org.example.mvc.controller.RequestMethod;
import org.example.mvc.view.JspViewResolver;
import org.example.mvc.view.View;
import org.example.mvc.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

import java.util.List;


@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    private final static Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);


    private List<HandlerAdapter> handlerAdapters;
    private List<ViewResolver> viewResolvers;

    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public void init() throws ServletException {
        requestMappingHandlerMapping = new RequestMappingHandlerMapping();
        requestMappingHandlerMapping.init();

        handlerAdapters = List.of(new SimpleControllerHandlerAdapter());
        viewResolvers = Collections.singletonList(new JspViewResolver());
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        logger.info("service started");

        try {
            Controller handler = requestMappingHandlerMapping.findHandler(new HandlerKey(RequestMethod.valueOf(request.getMethod()), request.getRequestURI()));
//            String file = handler.handleRequest(request, response);


            HandlerAdapter handlerAdapter = handlerAdapters.stream()
                    .filter(obj -> obj.isSupport(handler))
                    .findFirst()
                    .orElseThrow(() -> new ServletException("no adapter for "));

            ModelAndView modelAndView = handlerAdapter.handle(request, response, handler);

            for (ViewResolver viewResolver : viewResolvers) {
                View view = viewResolver.resolveView(modelAndView.getViewName());
                view.render(modelAndView.getModel(), request, response);
            }
//            RequestDispatcher requestDispatcher = request.getRequestDispatcher(file);
//            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("Exception {}", e.getMessage());
        }
    }


}

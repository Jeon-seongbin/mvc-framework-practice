package org.example.mvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private Object view;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView(String viewName) {
        view = viewName;
    }

    public Map<String, Object> getModel() {
        return Collections.unmodifiableMap(this.model);
    }

    public String getViewName() {
        if( view instanceof String){
            return (String)this.view;
        }
        return null;

    }
}

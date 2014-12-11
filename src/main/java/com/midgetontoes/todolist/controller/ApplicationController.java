package com.midgetontoes.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ApplicationController {
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @RequestMapping(value = "/", produces = {"application/json"})
    @ResponseBody
    public Endpoint index() {
        Map<RequestMappingInfo, HandlerMethod> mappings = requestMappingHandlerMapping.getHandlerMethods();
        List<String> endpoints = mappings.entrySet().stream().filter(entry ->
                        AnnotationUtils.isAnnotationDeclaredLocally(ExposesResourceFor.class, entry.getValue().getBeanType())
        ).map(entry -> AnnotationUtils.getAnnotation(entry.getValue().getBeanType(), RequestMapping.class).value()[0]).distinct().collect(Collectors.toList());
        return new Endpoint(endpoints);
    }

    @RequestMapping(value = "/", produces = {"text/html"})
    public String indexPage() {
        return "redirect:/index.html";
    }

    private static class Endpoint {
        private List<String> resources;

        public Endpoint(List<String> resources) {
            this.resources = resources;
        }

        public List<String> getResources() {
            return resources;
        }
    }
}

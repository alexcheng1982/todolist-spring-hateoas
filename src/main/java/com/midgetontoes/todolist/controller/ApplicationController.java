package com.midgetontoes.todolist.controller;

import com.midgetontoes.todolist.resource.IndexResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.core.Relation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ApplicationController {
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @RequestMapping(value = "/", produces = {"application/json"})
    @ResponseBody
    public IndexResource index() {
        String baseUri = ServletUriComponentsBuilder.fromCurrentRequest().build().toString();
        Map<RequestMappingInfo, HandlerMethod> mappings = requestMappingHandlerMapping.getHandlerMethods();
        Set<ResourceMapping> resourceMappings = mappings.entrySet().stream().filter(entry ->
                        AnnotationUtils.isAnnotationDeclaredLocally(ExposesResourceFor.class, entry.getValue().getBeanType())
        ).map(entry -> {
            Class<?> controllerType = entry.getValue().getBeanType();
            Class<?> modelType = AnnotationUtils.findAnnotation(controllerType, ExposesResourceFor.class).value();
            String resourceName = AnnotationUtils.findAnnotation(modelType, Relation.class).collectionRelation();
            return new ResourceMapping(controllerType, resourceName);
        }).collect(Collectors.toSet());
        return new IndexResource(baseUri, resourceMappings);
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

package com.midgetontoes.todolist.resource;

import com.midgetontoes.todolist.controller.ResourceMapping;
import com.sun.jndi.toolkit.url.Uri;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.net.URI;
import java.util.Set;

public class IndexResource extends Resource {
    private String baseUri;
    private Set<ResourceMapping> mappings;

    public IndexResource(String baseUri, Set<ResourceMapping> mappings) {
        super("");
        this.baseUri = baseUri != null && baseUri.endsWith("/") ? baseUri.substring(0, baseUri.length() - 1) : baseUri;
        this.mappings = mappings;
        addLinks();
    }

    private void addLinks() {
        mappings.forEach(mapping -> add(
                new Link(
                        baseUri +
                                AnnotationUtils.findAnnotation(mapping.getControllerClass(), RequestMapping.class).value()[0],
                        mapping.getResourceName())));
    }
}

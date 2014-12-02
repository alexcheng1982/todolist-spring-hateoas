package com.midgetontoes.todolist.resource;

import com.midgetontoes.todolist.controller.ItemRestController;
import com.midgetontoes.todolist.controller.ListRestController;
import com.midgetontoes.todolist.model.List;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

public class ListResource extends ResourceSupport {

    private final String name;

    public ListResource(List list) {
        this.name = list.getName();
        Long listId = list.getId();
        add(linkTo(methodOn(ItemRestController.class).readItems(listId)).withRel("items"));
    }

    public String getName() {
        return name;
    }
}

package com.midgetontoes.todolist.resource;

import com.midgetontoes.todolist.controller.ItemRestController;
import com.midgetontoes.todolist.controller.ListRestController;
import com.midgetontoes.todolist.model.List;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

public class ListResource extends ResourceSupport {
    private List list;

    public ListResource(List list) {
        this.list = list;
        Long listId = list.getId();
        add(linkTo(methodOn(ItemRestController.class, listId).readItems(listId)).withRel("items"));
    }

    public List getList() {
        return list;
    }
}

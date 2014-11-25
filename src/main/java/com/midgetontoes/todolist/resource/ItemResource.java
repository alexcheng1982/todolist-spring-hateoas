package com.midgetontoes.todolist.resource;


import com.midgetontoes.todolist.controller.ItemRestController;
import com.midgetontoes.todolist.model.Item;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

public class ItemResource extends ResourceSupport {
    private final Item item;

    public ItemResource(Item item) {
        this.item = item;
        Long itemId = item.getId();
        Long listId = item.getList().getId();
        add(linkTo(methodOn(ItemRestController.class, listId, itemId).readItem(listId, itemId)).withSelfRel());
        if (item.isCompleted()) {
            add(linkTo(methodOn(ItemRestController.class, listId, itemId).markAsUncompleted(listId, itemId)).withRel("mark-as-uncompleted"));
        }
        else {
            add(linkTo(methodOn(ItemRestController.class, listId, itemId).markAsCompleted(listId, itemId)).withRel("mark-as-completed"));
        }
    }

    public Item getItem() {
        return item;
    }
}

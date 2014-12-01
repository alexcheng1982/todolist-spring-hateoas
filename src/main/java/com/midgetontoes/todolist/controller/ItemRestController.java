package com.midgetontoes.todolist.controller;

import com.midgetontoes.todolist.jpa.ItemRepository;
import com.midgetontoes.todolist.model.Item;
import com.midgetontoes.todolist.model.List;
import com.midgetontoes.todolist.resource.ItemResource;
import com.midgetontoes.todolist.resource.ItemResourceAssembler;
import com.midgetontoes.todolist.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@ExposesResourceFor(Item.class)
@RequestMapping(value = "/lists/{listId}/items")
public class ItemRestController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private EntityLinks entityLinks;

    @RequestMapping(method = RequestMethod.GET)
    public Resources<ItemResource> readItems(@PathVariable Long listId) {
        return new Resources<ItemResource>(
                new ItemResourceAssembler().toResources(itemService.findByListId(listId))
        );
    }

    @RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
    public ItemResource readItem(@PathVariable Long listId, @PathVariable Long itemId) {
        return new ItemResourceAssembler().toResource(itemService.findOne(itemId));
    }

    @RequestMapping(value = "/{itemId}/markAsCompleted", method = RequestMethod.PUT)
    public ItemResource markAsCompleted(@PathVariable Long listId, @PathVariable Long itemId) {
        return new ItemResourceAssembler().toResource(itemService.markAsCompleted(itemId));
    }

    @RequestMapping(value = "/{itemId}/markAsUncompleted", method = RequestMethod.PUT)
    public ItemResource markAsUncompleted(@PathVariable Long listId, @PathVariable Long itemId) {
        return new ItemResourceAssembler().toResource(itemService.markAsUncompleted(itemId));
    }
}

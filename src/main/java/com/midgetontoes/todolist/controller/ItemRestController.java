package com.midgetontoes.todolist.controller;

import com.midgetontoes.todolist.jpa.ItemRepository;
import com.midgetontoes.todolist.model.Item;
import com.midgetontoes.todolist.resource.ItemResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/lists/{listId}/items")
public class ItemRestController {

    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Resources<ItemResource> readItems(@PathVariable Long listId) {
        return new Resources<ItemResource>(
                itemRepository.findByListId(listId)
                    .stream()
                    .map(ItemResource::new)
                    .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
    public ItemResource readItem(@PathVariable Long listId, @PathVariable Long itemId) {
        return new ItemResource(itemRepository.findOne(itemId));
    }

    @RequestMapping(value = "/{itemId}/markAsCompleted", method = RequestMethod.PUT)
    public Item markAsCompleted(@PathVariable Long listId, @PathVariable Long itemId) {
        Item item = itemRepository.findOne(itemId);
        if (item != null) {
            item.markAsCompleted();
            itemRepository.save(item);
        }
        return item;
    }

    @RequestMapping(value = "/{itemId}/markAsUncompleted", method = RequestMethod.PUT)
    public Item markAsUncompleted(@PathVariable Long listId, @PathVariable Long itemId) {
        Item item = itemRepository.findOne(itemId);
        if (item != null) {
            item.markAsUncompleted();
            itemRepository.save(item);
        }
        return item;
    }
}

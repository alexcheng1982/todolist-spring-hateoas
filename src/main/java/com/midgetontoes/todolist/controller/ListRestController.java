package com.midgetontoes.todolist.controller;

import com.midgetontoes.todolist.jpa.ListRepository;
import com.midgetontoes.todolist.model.List;
import com.midgetontoes.todolist.resource.ListResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lists")
public class ListRestController {

    @Autowired
    private  ListRepository listRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Resources<ListResource> readLists() {
        String username = "alex";
        java.util.List<ListResource> lists = listRepository.findByUserUsername(username)
                .stream()
                .map(ListResource::new)
                .collect(Collectors.toList());
        return new Resources<ListResource>(lists);
    }

    @RequestMapping(value = "/{listId}", method = RequestMethod.GET)
    public ListResource readList(@PathVariable Long listId) {
        return new ListResource(listRepository.findOne(listId));
    }
}

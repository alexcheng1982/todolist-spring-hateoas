package com.midgetontoes.todolist.controller;

import com.midgetontoes.todolist.jpa.ListRepository;
import com.midgetontoes.todolist.model.List;
import com.midgetontoes.todolist.resource.ListResource;
import com.midgetontoes.todolist.resource.ListResourceAssembler;
import com.midgetontoes.todolist.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lists")
public class ListRestController {

    @Autowired
    private ListService listService;

    @RequestMapping(method = RequestMethod.GET)
    public Resources<ListResource> readLists(Principal principal) {
        String username = principal.getName();
        ListResourceAssembler listResourceAssembler = new ListResourceAssembler();
        java.util.List<ListResource> lists = listService.findByUserUsername(username)
                .stream()
                .map(listResourceAssembler::toResource)
                .collect(Collectors.toList());
        return new Resources<ListResource>(lists);
    }

    @RequestMapping(value = "/{listId}", method = RequestMethod.GET)
    public ListResource readList(@PathVariable Long listId) {
        return new ListResourceAssembler().toResource(listService.findOne(listId));
    }
}

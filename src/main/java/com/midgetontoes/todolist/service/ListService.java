package com.midgetontoes.todolist.service;

import com.midgetontoes.todolist.jpa.ListRepository;
import com.midgetontoes.todolist.model.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListService {
    @Autowired
    private ListRepository listRepository;

    public List findOne(Long id) {
        return listRepository.findOne(id);
    }
}

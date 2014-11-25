package com.midgetontoes.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class List extends AbstractEntity {
    private String name;

    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "list", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Item> items = new HashSet<>();

    protected List() {
    }

    public List(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    public Set<Item> getItems() {
        return items;
    }
}

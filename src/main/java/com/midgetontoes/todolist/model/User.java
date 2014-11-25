package com.midgetontoes.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User extends AbstractEntity {
    private String username;

    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<List> lists = new HashSet<>();

    protected User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<List> getLists() {
        return lists;
    }
}

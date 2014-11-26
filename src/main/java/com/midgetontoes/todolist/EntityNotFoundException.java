package com.midgetontoes.todolist;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super("Entity not found!");
    }
}

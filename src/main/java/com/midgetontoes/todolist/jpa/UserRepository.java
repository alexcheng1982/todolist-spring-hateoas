package com.midgetontoes.todolist.jpa;

import com.midgetontoes.todolist.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}

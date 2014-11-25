package com.midgetontoes.todolist;

import com.fasterxml.jackson.databind.Module;
import com.midgetontoes.todolist.jpa.ItemRepository;
import com.midgetontoes.todolist.jpa.ListRepository;
import com.midgetontoes.todolist.jpa.UserRepository;
import com.midgetontoes.todolist.json.JacksonModule;
import com.midgetontoes.todolist.model.Item;
import com.midgetontoes.todolist.model.List;
import com.midgetontoes.todolist.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    //@Bean
    CommandLineRunner init(UserRepository userRepository, ListRepository listRepository, ItemRepository itemRepository) {
        return (evt) -> Arrays.asList("alex", "bob", "david")
                .forEach(username -> {
                    User user = userRepository.save(new User(username, "password"));
                    List list =  listRepository.save(new List("Default", user));
                    itemRepository.save(new Item("My first item.", list));
                });
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

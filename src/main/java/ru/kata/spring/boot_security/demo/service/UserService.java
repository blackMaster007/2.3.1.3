package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    void add(User user);
    List<User> listUsers();
    void remove(User user);
    User save(User newUser);
    User showUser(Long id);
}

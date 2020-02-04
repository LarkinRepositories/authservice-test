package com.authservice.authservice.service;

import com.authservice.authservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User register(User user);
    List<User> getAll();
    User findByUserName(String name);
    User findById(Long id);
    void delete(Long id);
}

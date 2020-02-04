package com.authservice.authservice.service.impl;

import com.authservice.authservice.model.Role;
import com.authservice.authservice.model.Status;
import com.authservice.authservice.model.User;
import com.authservice.authservice.repository.RoleRepository;
import com.authservice.authservice.repository.UserRepository;
import com.authservice.authservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
     public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        User registeredUser = userRepository.save(user);
        log.info("IN register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll  - {} users found", result.size());
        return  result;
    }

    @Override
    public User findByUserName(String name) {
        User user = userRepository.findByUsername(name);
        if (user == null) {
            log.warn("In findByUserName - no user found by name: {}", name);
            return null;
        }
        log.info("IN findByUserName - user: {} found by username: {}", user, name);
        return user;
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            log.warn("In findById - no user found by id: {}", id);
            return null;
        }
        log.info("In findById user: {} found by id: {}", user, id);
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("In delete - user with id: {} sucessfully deleted", id);
    }
}

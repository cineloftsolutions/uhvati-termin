package com.cineloftsolutions.uhvati_termin.controller;

import com.cineloftsolutions.uhvati_termin.entity.User;
import com.cineloftsolutions.uhvati_termin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/api/ping")
    public String ping() {
        return "Backend is working! ✅";
    }
}

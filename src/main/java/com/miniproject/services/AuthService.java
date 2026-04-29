package com.miniproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.miniproject.entities.User;
import com.miniproject.repositories.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //Register
    public String registerUser(User user) {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            return "Username already exists!";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        return "Registration successful!";
    }

    //Login
    public String loginUser(User user) {
        User existingUser = userRepo.findByUsername(user.getUsername());

        if (existingUser == null) {
            return "User not found!";
        }

        if (passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            return "Login successful!";
        } else {
            return "Invalid credentials!";
        }
    }
}

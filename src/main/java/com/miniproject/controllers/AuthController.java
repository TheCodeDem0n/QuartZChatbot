package com.miniproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.miniproject.entities.User;
import com.miniproject.services.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    //REGISTER
    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        return authService.registerUser(user);
    }

    //LOGIN
    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        return authService.loginUser(user);
    }
}

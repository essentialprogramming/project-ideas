package com.service;

import com.model.User;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public User authenticate(String username, String password) {
        if ((username.equals("admin") && password.equals("admin"))) {
            return new User(username, password);
        } else return null;
    }
}

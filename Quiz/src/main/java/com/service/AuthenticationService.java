package com.service;

import com.model.UserInput;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public UserInput authenticate(String username, String password) {
        if ((username.equals("admin") && password.equals("admin"))) {
            return new UserInput(username, password);
        } else return null;
    }
}

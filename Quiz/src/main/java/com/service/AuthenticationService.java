package com.service;

import com.entities.User;
import com.model.UserInput;
import com.repository.UserRepository;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    public UserInput authenticate(String username, String password) {
        if ((username.equals("ionpop@gmail.com") && password.equals("test"))) {
            return new UserInput(username, password);
        } else return null;
    }

    public void register(UserInput userInput) {
        User user = new User(userInput.getUsername(), userInput.getPassword());
        userRepository.save(user);

    }
}

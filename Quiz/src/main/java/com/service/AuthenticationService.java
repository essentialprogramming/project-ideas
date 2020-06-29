package com.service;

import com.entities.User;
import com.mapper.UserMapper;
import com.model.UserInput;
import com.output.UserJSON;
import com.repository.UserRepository;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserJSON authenticate(String username) {
        Optional<User> user = userRepository.findById(username);

        if (user.isPresent()) {
            return UserMapper.entityToJson(user.get());
        } else return null;
    }

    public void register(UserInput userInput) {
        User user = UserMapper.inputToEntity(userInput);
        userRepository.save(user);

    }
}

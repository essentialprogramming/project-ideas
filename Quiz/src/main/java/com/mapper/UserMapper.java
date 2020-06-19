package com.mapper;

import com.entities.User;
import com.model.UserInput;
import com.output.UserJSON;

public class UserMapper {

    public static User inputToEntity(UserInput input) {

        return User.builder()
                .username(input.getUsername())
                .password(input.getPassword())
                .year(input.getYear())
                .group(input.getPassword())
                .build();
    }

    public static UserJSON entityToJson(User user) {

        return UserJSON.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .year(user.getYear())
                .group(user.getGroup())
                .build();
    }
}

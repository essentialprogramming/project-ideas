package com.model;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInput {

    private String username;
    private String password;
    private int year;
    private String group;
}

package com.output;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserJSON {

    private String username;
    private String password;
    private int year;
    private String group;
}

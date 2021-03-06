package com.output;


import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizJSON {

    private int id;
    private String name;
    private String level;
    private List<String> students;
    private List<String> questions;
    private String date;
}

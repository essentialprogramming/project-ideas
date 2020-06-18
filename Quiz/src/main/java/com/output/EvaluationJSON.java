package com.output;


import lombok.*;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationJSON {

    private int id;
    private String score;
    private int quiz;
    private String student;
    private List<String> userAnswers;
}

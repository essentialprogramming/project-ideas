package com.output;


import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionJSON {

    private int id;
    private String question;
    private String correctAnswer;
    private String quiz;
    private List<String> answers;
}

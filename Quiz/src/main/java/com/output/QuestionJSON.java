package com.output;


import com.entities.Answer;
import com.entities.Quiz;
import lombok.*;

import javax.persistence.*;
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

package com.model;


import com.entities.Question;
import com.entities.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizInput {

    private String name;
    private String level;
    private List<QuestionInput> questions;

}

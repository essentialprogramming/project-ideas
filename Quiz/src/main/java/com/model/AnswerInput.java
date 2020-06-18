package com.model;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerInput {

    private int questionId;
    private String answer;
}

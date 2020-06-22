package com.service;


import com.mapper.QuestionMapper;
import com.output.QuestionJSON;
import com.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Transactional
    public List<QuestionJSON> getAll() {
        return questionRepository.findAll().stream().map(QuestionMapper::entityToJson).collect(Collectors.toList());
    }
}

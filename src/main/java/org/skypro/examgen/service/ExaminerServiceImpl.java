package org.skypro.examgen.service;

import org.skypro.examgen.exception.NotEnoughQuestionsException;
import org.skypro.examgen.model.Question;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final QuestionService QuestionService;

    public ExaminerServiceImpl(QuestionService QuestionService) {
        this.QuestionService = QuestionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        if (amount > QuestionService.getAll().size()) {
            throw new NotEnoughQuestionsException("Requested amount exceeds available questions");
        }
        Set<Question> result = new HashSet<>();
        while (result.size() < amount) {
            result.add(QuestionService.getRandomQuestion());
        }
        return result;
    }

    @Override
    public int getAllQuestionsCount() {
        return QuestionService.getAll().size();
    }
}
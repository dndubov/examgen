package org.skypro.examgen.service;

import org.skypro.examgen.exception.NotEnoughQuestionsException;
import org.skypro.examgen.model.Question;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final JavaQuestionService javaQuestionService;

    public ExaminerServiceImpl(JavaQuestionService javaQuestionService) {
        this.javaQuestionService = javaQuestionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        if (amount > javaQuestionService.getAll().size()) {
            throw new NotEnoughQuestionsException("Requested amount exceeds available questions");
        }
        Set<Question> result = new HashSet<>();
        while (result.size() < amount) {
            result.add(javaQuestionService.getRandomQuestion());
        }
        return result;
    }

    @Override
    public int getAllQuestionsCount() {
        return javaQuestionService.getAll().size(); // Добавьте этот метод
    }
}
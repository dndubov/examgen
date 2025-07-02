package org.skypro.examgen.service;

import org.skypro.examgen.model.Question;
import java.util.Collection;

public interface QuestionService {
    Question add(String question, String answer);
    Question remove(Question question);
    Collection<Question> getAll();
    Question getRandomQuestion();
}
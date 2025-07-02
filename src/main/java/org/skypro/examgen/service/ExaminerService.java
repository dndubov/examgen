package org.skypro.examgen.service;

import org.skypro.examgen.model.Question;
import java.util.Collection;

public interface ExaminerService {
    Collection<Question> getQuestions(int amount);
    int getAllQuestionsCount(); // Добавьте эту строку
}



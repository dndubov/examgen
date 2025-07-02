package org.skypro.examgen.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.examgen.exception.NotEnoughQuestionsException;
import org.skypro.examgen.model.Question;

import static org.junit.jupiter.api.Assertions.*;

class ExaminerServiceImplTest {
    private ExaminerServiceImpl examinerService;
    private JavaQuestionService javaService;

    @BeforeEach
    void setUp() {
        javaService = new JavaQuestionService();
        examinerService = new ExaminerServiceImpl(javaService);
    }

    @Test
    void getQuestions_ShouldThrowWhenNotEnoughQuestions() {
        // Добавляем только 1 вопрос
        javaService.add("Q1", "A1");

        // Пытаемся запросить 2 вопроса - должно выбросить исключение
        assertThrows(NotEnoughQuestionsException.class,
                () -> examinerService.getQuestions(2));
    }

    @Test
    void getQuestions_ShouldReturnExactAmount() {
        // Добавляем 2 вопроса
        javaService.add("Q1", "A1");
        javaService.add("Q2", "A2");

        // Запрашиваем 2 - должно работать
        assertEquals(2, examinerService.getQuestions(2).size());
    }
}
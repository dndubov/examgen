package org.skypro.examgen.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.examgen.exception.NotEnoughQuestionsException;
import org.skypro.examgen.model.Question;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    @Test
    void getQuestions_ShouldThrowWhenNotEnoughQuestions() {
        // Arrange
        Question existingQuestion = new Question("Q1", "A1");
        when(questionService.getAll()).thenReturn(Set.of(existingQuestion));

        // Act & Assert
        Exception exception = assertThrows(NotEnoughQuestionsException.class,
                () -> examinerService.getQuestions(2));

        assertEquals("Requested amount exceeds available questions", exception.getMessage());
    }

    @Test
    void getQuestions_ShouldReturnExactAmountAndCorrectQuestions() {
        // Arrange
        Question q1 = new Question("Что такое ООП?", "Объектно-ориентированное программирование");
        Question q2 = new Question("Что такое полиморфизм?", "Возможность объектов с одинаковым интерфейсом иметь разную реализацию");

        when(questionService.getAll()).thenReturn(Set.of(q1, q2));
        when(questionService.getRandomQuestion())
                .thenReturn(q1)
                .thenReturn(q2);

        // Act
        Collection<Question> result = examinerService.getQuestions(2);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(q1));
        assertTrue(result.contains(q2));

        // Проверяем содержимое вопросов
        for (Question question : result) {
            assertNotNull(question.getQuestion());
            assertNotNull(question.getAnswer());
            assertFalse(question.getQuestion().isEmpty());
            assertFalse(question.getAnswer().isEmpty());
        }
    }

    @Test
    void getQuestions_ShouldReturnUniqueQuestions() {
        // Arrange
        Question q1 = new Question("Q1", "A1");
        Question q2 = new Question("Q2", "A2");

        when(questionService.getAll()).thenReturn(Set.of(q1, q2));
        when(questionService.getRandomQuestion())
                .thenReturn(q1)
                .thenReturn(q2);

        // Act
        Collection<Question> result = examinerService.getQuestions(2);

        // Assert
        assertEquals(2, result.size());
        assertEquals(2, result.stream().distinct().count());
    }

    @Test
    void getAllQuestionsCount_ShouldReturnCorrectCount() {
        // Arrange
        when(questionService.getAll()).thenReturn(Set.of(
                new Question("Q1", "A1"),
                new Question("Q2", "A2")
        ));

        // Act
        int count = examinerService.getAllQuestionsCount();

        // Assert
        assertEquals(2, count);
    }
}
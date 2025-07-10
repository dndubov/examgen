package org.skypro.examgen.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.examgen.exception.NotEnoughQuestionsException;
import org.skypro.examgen.model.Question;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class JavaQuestionServiceTest {
    private final JavaQuestionService service = new JavaQuestionService();

    @Test
    void add_ShouldReturnQuestionWithCorrectContent() {
        // Act
        Question result = service.add("Что такое ООП?", "Объектно-ориентированное программирование");

        // Assert
        assertThat(result)
                .isNotNull()
                .extracting(Question::getQuestion, Question::getAnswer)
                .containsExactly("Что такое ООП?", "Объектно-ориентированное программирование");
    }

    @Test
    void add_ShouldStoreQuestionInRepository() {
        // Arrange
        Question question = new Question("Что такое полиморфизм?",
                "Свойство системы использовать объекты с одинаковым интерфейсом");

        // Act
        service.add(question.getQuestion(), question.getAnswer());

        // Assert
        assertThat(service.getAll())
                .hasSize(1)
                .first()
                .usingRecursiveComparison()
                .isEqualTo(question);
    }

    @Test
    void remove_ShouldDeleteExactQuestion() {
        // Arrange
        Question q1 = service.add("Q1", "A1");
        Question q2 = service.add("Q2", "A2");

        // Act
        Question removed = service.remove(q1);

        // Assert
        assertThat(removed).isEqualTo(q1);
        assertThat(service.getAll())
                .hasSize(1)
                .containsExactly(q2);
    }

    @Test
    void getRandomQuestion_ShouldReturnExistingQuestion() {
        // Arrange
        Question expected = service.add("Как работает HashMap?",
                "На основе хэш-таблицы с разрешением коллизий");

        // Act
        Question actual = service.getRandomQuestion();

        // Assert
        assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void getRandomQuestion_ShouldReturnDifferentQuestions() {
        // Arrange
        service.add("Q1", "A1");
        service.add("Q2", "A2");
        service.add("Q3", "A3");

        // Act
        Question first = service.getRandomQuestion();
        Question second = service.getRandomQuestion();

        // Assert (может иногда падать из-за природы рандома)
        assertThat(first).isNotEqualTo(second);
    }

    @Test
    void getAll_ShouldReturnDefensiveCopy() {
        // Arrange
        service.add("Q1", "A1");
        Collection<Question> first = service.getAll();

        // Act
        service.add("Q2", "A2");
        Collection<Question> second = service.getAll();

        // Assert
        assertThat(first)
                .hasSize(1)
                .isNotEqualTo(second);
    }
}
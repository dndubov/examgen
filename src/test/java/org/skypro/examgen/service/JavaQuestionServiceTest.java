package org.skypro.examgen.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.examgen.exception.NotEnoughQuestionsException;
import org.skypro.examgen.model.Question;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;

class JavaQuestionServiceTest {
    private JavaQuestionService service;

    @BeforeEach
    void setUp() {
        service = new JavaQuestionService();
    }

    @Test
    void add_ShouldAddQuestion() {
        Question q = service.add("Q1", "A1");
        assertTrue(service.getAll().contains(q));
    }

    @Test
    void remove_ShouldDeleteQuestion() {
        Question q = service.add("Q2", "A2");
        assertEquals(q, service.remove(q));
        assertFalse(service.getAll().contains(q));
    }

    @Test
    void getRandomQuestion_ShouldReturnQuestion() {
        service.add("Q3", "A3");
        assertNotNull(service.getRandomQuestion());
    }

    @Test
    void getRandomQuestion_ShouldThrowWhenEmpty() {
        assertThrows(NotEnoughQuestionsException.class, () -> service.getRandomQuestion());
    }
}
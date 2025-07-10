package org.skypro.examgen.controller;

import org.skypro.examgen.exception.NotEnoughQuestionsException;
import org.skypro.examgen.model.Question;
import org.skypro.examgen.service.ExaminerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Collection;

@RestController
@RequestMapping("/exam")
public class ExamController {
    private final ExaminerService examinerService;

    public ExamController(ExaminerService examinerService) {
        this.examinerService = examinerService;
    }

    @GetMapping("/get/{amount}")
    public ResponseEntity<?> getQuestions(@PathVariable int amount) {
        try {
            Collection<Question> questions = examinerService.getQuestions(amount);
            return ResponseEntity.ok(questions);
        } catch (NotEnoughQuestionsException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error", e.getMessage(),
                            "available", examinerService.getAllQuestionsCount()
                    ));
        }
    }
}
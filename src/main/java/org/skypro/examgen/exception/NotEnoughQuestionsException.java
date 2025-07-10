package org.skypro.examgen.exception;

public class NotEnoughQuestionsException extends RuntimeException {
    public NotEnoughQuestionsException(String message) {
        super(message);
    }
}
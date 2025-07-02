package org.skypro.examgen.service;

import org.skypro.examgen.exception.NotEnoughQuestionsException;
import org.skypro.examgen.model.Question;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class JavaQuestionService implements QuestionService {
    private final Set<Question> questions = new HashSet<>();
    private final Random random = new Random();

    @Override
    public Question add(String question, String answer) {
        Question newQuestion = new Question(question, answer);
        questions.add(newQuestion);
        return newQuestion;
    }

    @Override
    public Question remove(Question question) {
        if (questions.remove(question)) {
            return question;
        }
        return null;
    }

    @Override
    public Collection<Question> getAll() {
        return Collections.unmodifiableSet(questions);
    }

    @Override
    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            throw new NotEnoughQuestionsException("No questions available");
        }
        return new ArrayList<>(questions).get(random.nextInt(questions.size()));
    }
}
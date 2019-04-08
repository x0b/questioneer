package io.github.x0b.questioneer.repository;

import io.github.x0b.questioneer.model.Answer;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface AnswerRepository {
    /**
     * Get a Questions by id
     * @param id Answer Id
     * @return an Answer
     */
    Optional<Answer> findById(@NotNull Long id);

    /**
     * Get all Answers
     * @return all @{@link Answer}s  as @{@link List}
     */
    List<Answer> findAll();

    /**
     * Persist an @{@link Answer}
     * @param answer - an Answer
     */
    void save(Answer answer);

    void save(List<Answer> answer);
}

package io.github.x0b.questioneer.repository;

import io.github.x0b.questioneer.model.Question;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    /**
     * Retrieve a Question by id
     * @param id - question database id
     * @return an @{@link Optional< Question >}
     */
    Optional<Question> findById(@NotNull Long id);

    /**
     * Retrieve all Questions
     * @return all @{@link Question}s from the db
     */
    List<Question> findAll();

    void save(Question question);

}

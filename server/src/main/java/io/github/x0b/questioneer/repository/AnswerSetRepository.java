package io.github.x0b.questioneer.repository;

import io.github.x0b.questioneer.model.AnswerSet;

import java.util.List;

public interface AnswerSetRepository {

    List<AnswerSet> findAll();

    /**
     * Persist an AnswerSet
     * @param answer - an @{@link AnswerSet} to persist
     */
    void save(AnswerSet answer);

    List<List<String>> getAnswerSetsNormalized();
}

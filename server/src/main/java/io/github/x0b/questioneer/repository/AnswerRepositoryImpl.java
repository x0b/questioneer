package io.github.x0b.questioneer.repository;

import io.github.x0b.questioneer.model.Answer;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Singleton
public class AnswerRepositoryImpl implements AnswerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public AnswerRepositoryImpl(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Answer> findById(@NotNull Long id) {
        return Optional.ofNullable(entityManager.find(Answer.class, id));
    }

    @Override
    public List<Answer> findAll() {
        return entityManager.createQuery("SELECT s FROM answers as a", Answer.class).getResultList();
    }

    @Override
    @Transactional
    public void save(Answer answer) {
        entityManager.persist(answer);
    }

    @Transactional
    public void save(List<Answer> answers){
        answers.forEach(this::save);
    }
}

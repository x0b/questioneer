package io.github.x0b.questioneer.repository;

import io.github.x0b.questioneer.model.Question;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Singleton
public class QuestionRepositoryImpl implements QuestionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public QuestionRepositoryImpl(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Question> findById(@NotNull Long id) {
        return Optional.ofNullable(entityManager.find(Question.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> findAll() {
        return entityManager.createQuery("SELECT q FROM Question as q", Question.class).getResultList();
    }

    @Override
    @Transactional(readOnly = false)
    public void save(Question question) {
        entityManager.persist(question);
    }
}

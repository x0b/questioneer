package io.github.x0b.questioneer.repository;

import io.github.x0b.questioneer.model.AnswerSet;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnswerSetRepositoryImpl implements AnswerSetRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private SessionFactory sessionFactory;

    public AnswerSetRepositoryImpl(@CurrentSession EntityManager entityManager, SessionFactory sessionFactory) {
        this.entityManager = entityManager;
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AnswerSet> findAll() {
        return entityManager.createQuery("SELECT s FROM AnswerSet as s", AnswerSet.class).getResultList();
    }

    @Transactional(readOnly = false)
    @Override
    public void save(AnswerSet answerSet) {
        entityManager.persist(answerSet);
    }

    @Transactional(readOnly = true)
    @Override
    public List<List<String>> getAnswerSetsNormalized() {
        List<List<String>> answerSets = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        //Transaction transaction = session.beginTransaction();
        Query query = session.createSQLQuery("SELECT \n" +
                "\tanswers.answerSetId as 'QuestionRound',\n" +
                "\tquestions.text as 'Question',\n" +
                "\tquestions.type as 'QuestionType',\n" +
                "\tAnswerItem.value as 'AnswerValue',\n" +
                "\tAnswerOption.text as 'AnswerDescription'\n" +
                "FROM AnswerItem\n" +
                "LEFT JOIN answers on AnswerItem.answerId=answers.id\n" +
                "LEFT JOIN questions on answers.question_id=questions.id\n" +
                "LEFT JOIN AnswerOption on AnswerOption.questionId=questions.id AND AnswerItem.value=AnswerOption.key");
        List rows = query.getResultList();
        //transaction.commit();
        answerSets.add(Arrays.asList(
                "QuestionRound",
                "Question",
                "QuestionType",
                "AnswerValue",
                "AnswerDescription"
        ));
        for (Object untypedRow : rows) {
            Object[] row;
            if(!(untypedRow instanceof Object[])) {
                continue;
            } else {
                row = (Object[]) untypedRow;
            }
            answerSets.add(Arrays.asList(
                    emptyNull(row[0]),
                    emptyNull(row[1]),
                    emptyNull(row[2]),
                    emptyNull(row[3]),
                    emptyNull(row[4])
            ));
        }
        return answerSets;
    }

    private String emptyNull(Object str) {
        return str != null ? str.toString() : "";
    }
}

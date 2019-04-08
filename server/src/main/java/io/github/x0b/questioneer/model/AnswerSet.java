package io.github.x0b.questioneer.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class AnswerSet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "answerSet")
    private List<Answer> answersList;

    public AnswerSet(List<Answer> answersList) {
        this.answersList = answersList;
    }

    public AnswerSet() {
    }

    public List<Answer> getAnswersList() {
        return answersList;
    }

    public long getId() {
        return id;
    }
}

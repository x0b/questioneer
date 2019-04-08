package io.github.x0b.questioneer.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private Question question;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "answer")
    private List<AnswerItem> answers;

    @ManyToOne
    @JoinColumn(name = "answerSetId")
    private AnswerSet answerSet;

    public Answer() {
    }

    public Answer(Question question, List<AnswerItem> answers) {
        this.question = question;
        this.answers = answers;
    }

    public long getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public List<AnswerItem> getAnswers() {
        return answers;
    }

    public AnswerSet getAnswerSet() {
        return answerSet;
    }

    public void setAnswerSet(AnswerSet answerSet) {
        this.answerSet = answerSet;
    }
}

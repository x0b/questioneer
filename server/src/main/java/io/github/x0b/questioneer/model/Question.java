package io.github.x0b.questioneer.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Enumerated(EnumType.STRING)
    private QuestionType type;
    /**
     * Answer options for the questions
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "question")
    private List<AnswerOption> options;
    /**
     * Question Description
     */
    private String text;

    public Question(QuestionType type, List<AnswerOption> options, String text) {
        this.type = type;
        this.options = options;
        this.text = text;
    }

    public Question() {
    }

    public long getId() {
        return id;
    }

    public QuestionType getType() {
        return type;
    }

    public List<AnswerOption> getOptions() {
        return options;
    }

    public String getText() {
        return text;
    }
}

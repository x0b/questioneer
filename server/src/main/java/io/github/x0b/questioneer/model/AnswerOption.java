package io.github.x0b.questioneer.model;

import javax.persistence.*;

/**
 * An option that can be filled out/selected.
 */
@Entity
public class AnswerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String key;
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "questionId")
    private Question question;

   public AnswerOption(String key, String text, Question question) {
        this.key = key;
        this.text = text;
        this.question = question;
    }

    public AnswerOption() {
    }

    public long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getText() {
        return text;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}

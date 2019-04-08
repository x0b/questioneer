package io.github.x0b.questioneer.model;

import javax.persistence.*;

/**
 * An value item of an Answer. Some questions allow users to select multiple AnswerItems.
 */
@Entity
public class AnswerItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "answerId")
    private Answer answer;

    public AnswerItem() {
    }

    public AnswerItem(String value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}

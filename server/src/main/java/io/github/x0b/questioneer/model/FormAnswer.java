package io.github.x0b.questioneer.model;

import java.util.List;

public class FormAnswer {
    private long questionid;
    private List<AnswerOption> options;

    public long getQuestionid() {
        return questionid;
    }

    public void setQuestionid(long questionid) {
        this.questionid = questionid;
    }

    public List<AnswerOption> getOptions() {
        return options;
    }

    public void setOptions(List<AnswerOption> options) {
        this.options = options;
    }
}

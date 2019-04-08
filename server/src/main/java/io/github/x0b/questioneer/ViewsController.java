package io.github.x0b.questioneer;

import io.github.x0b.questioneer.model.*;
import io.github.x0b.questioneer.repository.AnswerRepository;
import io.github.x0b.questioneer.repository.QuestionRepository;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.server.types.files.AttachedFile;
import io.micronaut.views.View;
import io.github.x0b.questioneer.repository.AnswerSetRepository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ViewsController {


    private QuestionRepository questionRepository;

    private AnswerRepository answerRepository;

    private AnswerSetRepository answerSetRepository;

    public ViewsController(QuestionRepository questionRepository, AnswerRepository answerRepository, AnswerSetRepository answerSetRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.answerSetRepository = answerSetRepository;
    }

    @View("index")
    @Get("/")
    public HttpResponse index() {
        List<Question> questions = questionRepository.findAll();
        return HttpResponse.ok(CollectionUtils.mapOf( "questions", questions));
    }

    @View("questions")
    @Get("/questions")
    public HttpResponse questions(){
        return HttpResponse.ok();
    }

    @Post(value = "/save-responses", consumes = MediaType.APPLICATION_FORM_URLENCODED)
    public MutableHttpResponse saveIntervieweeResponses(@Body ResponseForm answers){
        Map<Long,Question> questions = questionRepository.findAll().stream().collect(Collectors.toMap(Question::getId, q -> q));
        List<Answer> persistableAnswers = new ArrayList<>(answers.getAnswers().size());
        for(FormAnswer a : answers.getAnswers()){
            if(null == a){
                continue;
            }
            Question question = questions.get(a.getQuestionid());
            if(null == question){
                continue;
            }
            Map<Long, AnswerOption> options = question.getOptions().stream().collect(Collectors.toMap(AnswerOption::getId, e -> e));
            persistableAnswers.add(new Answer(
                    question,
                    a.getOptions().stream()
                            .filter(Objects::nonNull)
                            .map(o -> new AnswerItem(o.getKey()))
                            .collect(Collectors.toList())
            ));
            // Link AnswerItems back to their Answers
            persistableAnswers.forEach(p -> p.getAnswers().forEach(i -> i.setAnswer(p)));
            //System.out.println(persistableAnswers);
        }

        //System.out.println(persistableAnswers);
        AnswerSet answerSet = new AnswerSet(persistableAnswers);
        persistableAnswers.forEach(p -> p.setAnswerSet(answerSet));
        answerSetRepository.save(answerSet);
        return HttpResponse.seeOther(URI.create("http://localhost:8080/"));
    }

    @Post(value = "/add-question", consumes = MediaType.APPLICATION_FORM_URLENCODED)
    public MutableHttpResponse saveQuestion(@Body Question question){
        //System.out.println(question);
        Question persistedQuestion = new Question(
                question.getType(),
                question.getOptions().stream()
                        .filter(Objects::nonNull)
                        .filter(option -> option.getText() != null && option.getText().length() > 0)
                        .collect(Collectors.toList()),
                question.getText()
        );
        // Link Question to option
        persistedQuestion.getOptions().forEach(option -> option.setQuestion(persistedQuestion));
        questionRepository.save(persistedQuestion);
        return HttpResponse.seeOther(URI.create("http://localhost:8080/questions"));
    }

    @Get("/denormalized-csv")
    public AttachedFile answersAsDenormilizedCsv() throws IOException {

        File file = File.createTempFile("denom-answers-csv", "temp");
        try(FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw)){
            answerSetRepository.getAnswerSetsNormalized()
                .stream().map(row -> row.stream().map(field -> {
                    if (containsAnyOf(field, new String[]{"\"", ",", "\r\n", "\n"})) {
                        field = field.replace("\"", "\"\"");
                        return "\"" + field + "\"";
                    }
                   return field;
                }).collect(Collectors.joining(","))).forEach(line -> {
                try {
                    bw.write(line);
                    bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return new AttachedFile(file, "denormalized-answers.csv");
    }

    private boolean containsAnyOf(String contains, String[] anyOf){
        for(String any : anyOf){
            if(contains.contains(any)){
                return true;
            }
        }
        return false;
    }
}

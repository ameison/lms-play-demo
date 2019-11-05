package models;

import io.ebean.Finder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "content_quiz_answer")
public class ContentQuizAnswer extends BaseModel {

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "content_quiz_answer_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @ManyToOne
    public ContentQuiz contentQuiz;

    @Column(length = 1000)
    public String answer;

    @Column(name = "order_answer")
    public int orderAnswer;

    @Column(name = "is_correct_answer")
    public boolean isCorrectAnswer;

    public static Finder<Long, ContentQuizAnswer> find = new Finder<Long, ContentQuizAnswer>(ContentQuizAnswer.class);

    public static List<ContentQuizAnswer> getAnswersByContentQuiz(Long idConteQuiz){
        return ContentQuizAnswer.find.query().where().eq("content_quiz_id", idConteQuiz).orderBy("order_answer asc").findList();
    }


}

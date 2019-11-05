package models;

import io.ebean.Finder;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "content_quiz")
public class ContentQuiz extends BaseModel {

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "content_quiz_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @OneToOne
    @Column(unique=true)
    public Content content;

    @Column(length = 1000)
    public String question;

    @OneToMany(mappedBy = "contentQuiz")
    public List<ContentQuizAnswer> contentsQuizAnswer;

    public ContentQuiz(Long id){
        this.id = id;
    }

    public ContentQuiz(){

    }

    public static Finder<Long, ContentQuiz> find = new Finder<Long, ContentQuiz>(ContentQuiz.class);

    public ContentQuiz getContentQuiz(Long contentId){
        return ContentQuiz.find.query().where().eq("content_id", contentId).findOne();
    }

    public static ContentQuiz getQuizByContentId(Long contentId){
        return ContentQuiz.find.query().where().eq("content_id", contentId).findOne();
    }

    public static ContentQuiz getQuizById(Long id){
        return ContentQuiz.find.byId(id);
    }

    public UserProgress getUserProgress(String userEmail){
        return UserProgress.getProgressByUserContent(User.findByEmail(userEmail).id, content.id);
    }

}

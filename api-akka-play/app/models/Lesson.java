package models;


import io.ebean.Finder;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lesson")
public class Lesson extends BaseModel {

    public static final String INICIADO = "1";
    public static final String TERMINADO = "2";

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "lesson_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @Column(length = 45)
    public String name;

    @Column(name = "order_lesson")
    public int orderLesson;

    @ManyToOne
    public Chapter chapter;

    @OneToMany(mappedBy = "lesson")
    public List<Content> contents;

    public Lesson(){}

    public Lesson(Long id){
        this.id = id;
    }

    public static Finder<Long, Lesson> find = new Finder<Long, Lesson>(Lesson.class);

    public boolean isClosedLesson(Long userId){
        if(contents.size()>0){
            if(contents.size() == getClosedContents(userId)){
                return true;
            }
        }
        return false;
    }

    public static Lesson getbyId(Long lessonId){
        return Lesson.find.byId(lessonId);
    }

    public String getCourseCodeParent(){
        return chapter.course.code;
    }

    public static List<Lesson> getLessonsByChapterId(Long idChapter){
        return Lesson.find.query().where().eq("chapter_id", idChapter).findList();
    }

    public int getClosedContents(Long userId){

        int count = 0;
        //long userId = ApplicationController.getUserIdSession();

        for (Content content: contents) {
            if (UserProgress.isClosedContent(userId, content.id)) {
                count++;
            }
        }
        return count;
    }

    public String getStatus(Long userId){

        int metaContents = contents.size();
        int closedContents= getClosedContents(userId);

        if(closedContents > 0 && closedContents < metaContents){
            return INICIADO;
        }else if(metaContents > 0 && closedContents == metaContents){
            return TERMINADO;
        }

        return null;
    }
}

package models;

import io.ebean.Ebean;
import io.ebean.Finder;
import io.ebean.SqlRow;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "content")
public class Content extends BaseModel {

    public static final int VIDEO = 1;
    public static final int QUIZ = 2;
    public static final int EXAMPLE = 3;
    public static final int EXERCISE = 4;
    public static final int HOMEWORK = 5;

    public static final int DOWNLOAD = 6;

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "content_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @ManyToOne
    public Lesson lesson;

    @Column(length = 45)
    public String name;

    public int credit;

    @Column(name = "order_content")
    public int orderContent;

    @Column(name = "type_content")
    public int typeContent;

    @OneToOne(mappedBy = "content")
    public ContentVideo contentVideo;

    @OneToOne(mappedBy = "content")
    public ContentQuiz contentQuiz;

    @OneToOne(mappedBy = "content")
    public ContentGame contentGame;

    @OneToOne(mappedBy = "content")
    public ContentHomework contentHomework;

    @OneToOne(mappedBy = "content")
    public ContentDownload contentDownload;

    public static Finder<Long, Content> find = new Finder<>(Content.class);

    public static Content getContent(Long contentId){

        return Content.find.query().where().eq("id", contentId).findOne();
    }

    public static List<Content> getAllContentXLesson(Long contentId){

        Content con = getContent(contentId);
        List<Content> lstContent = Content.find.query().where().eq("lesson_id", con.lesson.id).findList();

        return lstContent;
    }

    public static List<Content> getAllContentXIdLessson(Long idLesson){
        List<Content> lstContent = Content.find.query().where().eq("lesson_id", idLesson).orderBy("order_content asc").findList();
        return lstContent;
    }

    public static Content getContentxOrden(Long idLesson, int orderContent){
        return Content.find.query().where().eq("lesson_id", idLesson).eq("order_content",
                orderContent).findOne();
    }

    public UserProgress getProgress(Long userId){
        UserProgress.UserContentProgressKey pk = new UserProgress.UserContentProgressKey();
        pk.userId = userId;
        pk.contentId = id;
        UserProgress myprogress = UserProgress.find.byId(pk);
        return myprogress;
    }

    public static int getTopPosition(Long lessonId){
        String sql = "select max(order_content) as max_order from content  where lesson_id ="+lessonId;
        SqlRow a = Ebean.createSqlQuery(sql).findOne();
        return a.getInteger("max_order") + 1;
    }

    public static int getContentsBadgesValid(){

        List<Content> lstContent = Content.find.all();//ESTa LISTA DEBE SER FILTRADA POR CURSO
        int sumContent = 0;

        for (int i = 0; i < lstContent.size(); i++) {
            int typeContent = lstContent.get(i).typeContent;
            if(typeContent == VIDEO || typeContent == QUIZ || typeContent == EXERCISE){
                sumContent++;
            }
        }
        return sumContent;
    }



}

package models;

import io.ebean.Ebean;
import io.ebean.Finder;
import io.ebean.SqlRow;
import play.db.ebean.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "content_homework")
public class ContentHomework  extends BaseModel  {
    @Id
    @SequenceGenerator(name = "generator", sequenceName = "content_homework_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @OneToOne
    @Column(unique=true)
    public Content content;

    @Column(length = 100)
    public String title;

    @Column(length = 100,columnDefinition = "TEXT")
    public String shortdescription;

    @Column(length = 500,columnDefinition = "TEXT")
    public String longdescription;

    @Column(name = "url_homework")
    public String urlhomework;

    @OneToOne
    public User instructor;

    @OneToMany(mappedBy = "homework")
    public List<HomeworkStudent> homeworkStudent;

    public static Finder<Long, ContentHomework> find = new Finder<Long, ContentHomework>(ContentHomework.class);

    public ContentHomework getContentHomework(Long contentId){
        return ContentHomework.find.query().where().eq("content_id", contentId).findOne();
    }
    public ContentHomework(){}
    public ContentHomework(SqlRow row){
        this.id = row.getLong("id");
        this.title = row.getString("title");
        this.shortdescription = row.getString("shortdescription");
        this.urlhomework = row.getString("url_homework");
    }

    @Transactional
    public static List<ContentHomework> getContentHomeworkByStudent(Long userId){
         String query = "select  content_homework.id,content_homework.content_id,"+
            "content_homework.title,content_homework.shortdescription,"+
            "content_homework.url_homework "+
            "from content_homework "+
            "inner join content "+
              "on (content.id = content_homework.content_id) "+
              "where content_homework.content_id in ("+
              "select content.id from content "+
              "inner join lesson "+
              "on (content.lesson_id = lesson.id) "+
              "inner join chapter "+
              "on (lesson.chapter_id = chapter.id) "+
              "inner join user_course "+
              "on (chapter.course_id = user_course.course_id)"+
              "where user_course.user_id =" + (userId).toString()+")";

        List<ContentHomework> homeworks = new ArrayList<ContentHomework>();
        List<SqlRow> sqlRows= Ebean.createSqlQuery(query).findList();
        for(SqlRow row : sqlRows) {
            homeworks.add(new ContentHomework(row));
        }
        return homeworks;
    }

    public static List<ContentHomework> getContentHomeworkByStudentAndCourse(Long userId,Long courseId){
        String query = "select  content_homework.id,content_homework.content_id,"+
                "content_homework.title,content_homework.shortdescription,"+
                "content_homework.url_homework "+
                "from content_homework "+
                "inner join content "+
                "on (content.id = content_homework.content_id) "+
                "where content_homework.content_id in ("+
                "select content.id from content "+
                "inner join lesson "+
                "on (content.lesson_id = lesson.id) "+
                "inner join chapter "+
                "on (lesson.chapter_id = chapter.id) "+
                "inner join user_course "+
                "on (chapter.course_id = user_course.course_id)"+
                "where user_course.user_id =" + (userId).toString()+
                " and user_course.course_id ="+(courseId).toString() +")";

        List<ContentHomework> homeworks = new ArrayList<ContentHomework>();
        List<SqlRow> sqlRows= Ebean.createSqlQuery(query).findList();
        for(SqlRow row : sqlRows) {
            homeworks.add(new ContentHomework(row));
        }
        return homeworks;
    }

    @Transactional
    public static List<ContentHomework> getContentHomeworkByTeacher(Long teacherId){
        String query = "select  content_homework.id,content_homework.content_id," +
                                "content_homework.title,content_homework.shortdescription," +
                                "content_homework.url_homework " +
                                "from content_homework " +
                                "inner join content " +
                                "on (content.id = content_homework.content_id) " +
                                "where content_homework.content_id in (" +
                                "select content.id from content " +
                                "inner join lesson " +
                                "on (content.lesson_id = lesson.id) " +
                                "inner join chapter " +
                                "on (lesson.chapter_id = chapter.id) " +
                "inner join course_instructor " +
                "on (chapter.course_id = course_instructor.course_id) " +
                "where course_instructor.instructor_id =" + (teacherId).toString()+")";

        List<ContentHomework> homeworks = new ArrayList<ContentHomework>();
        List<SqlRow> sqlRows= Ebean.createSqlQuery(query).findList();
        for(SqlRow row : sqlRows) {
            homeworks.add(new ContentHomework(row));
        }
        return homeworks;
    }









}

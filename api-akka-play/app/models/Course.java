package models;

import io.ebean.Ebean;
import io.ebean.Finder;
import io.ebean.SqlRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "course")
public class Course extends BaseModel {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "course_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @Column(unique = true, length = 15)
    public String code;

    @Column(length = 50)
    public String name;

    @Column(columnDefinition = "TEXT")
    public String description;

    @Column(name = "url_image")
    public String urlImage;

    @Column(name = "url_video")
    public String urlVideo;

    @Column(name = "course_free")
    public Boolean courseFree;

    @Column
    public Date startDate;

    @Column(name = "course_dependency")
    public String courseDependency;

    @OneToMany(mappedBy = "course")
    public List<Chapter> chapters;

    @OneToMany(mappedBy = "course")
    public List<UserCourse> usersCourse;

    @OneToMany(mappedBy = "course")
    public List<CourseLibrary> coursesLaunched;

    @OneToMany(mappedBy = "course")
    public List<CourseInstructor> instructores;

    @ManyToOne
    public Academy academy;

    public Course(){}

    public Course(Long id){ this.id = id; }

    public Course(SqlRow row){
        this.id = row.getLong("id");
        this.code = row.getString("code");
        this.name = row.getString("name");
        this.description = row.getString("description");
        this.urlImage = row.getString("url_Image");
        this.courseFree = Boolean.getBoolean(row.getString("courseFree"));
        this.usersCourse = UserCourse.getStudentsByCourse(row.getLong("id"));
    }

    public static Finder<Long,Course> find = new Finder<Long,Course>(Course.class);


    public static List<Course> getCoursesFromUser(long userId){
        List<Course>  lstMyCourses = find.query().where().
                //join("usersCourse").
                where().eq("user.id", userId).orderBy("course.id asc").findList();
        return lstMyCourses;
    }

    public static List<Course> getCoursesByInstructor(Long userId,Boolean isacademy){
        String consulta ="select co.id,code,co.name," +
                "co.description,co.course_free,co.academy_id, co.url_Image " +
                "from course co where co.id in " +
                "( select  ci.course_id from course_instructor ci where ci.instructor_id =" + (userId).toString()+" )";
        String  academy = " and co.academy_id is not null ";
        String  notAcademy = " and co.academy_id is null ";
        if(isacademy){
            consulta = consulta + academy;
        }
        else{
            consulta = consulta + notAcademy;
        }

        List<Course> cursos = new ArrayList<Course>();
        List<SqlRow> sqlRows= Ebean.createSqlQuery(consulta).findList();
        for(SqlRow row : sqlRows) {
            cursos.add(new Course(row));
        }

        return cursos;
    }

    public static List<Course> getCoursesByStudent(Long userId, Boolean isacademy){
        String consulta = "select co.id,code,co.name, " +
                                "co.description,co.course_free,co.academy_id, co.url_Image " +
                                "from course co where co.id in " +
                                "( select  uc.course_id from user_course uc where user_id= "+ userId +" )";
        String  academy = " and co.academy_id is not null ";
        String  notAcademy = " and co.academy_id is null ";
        if(isacademy){
            consulta = consulta + academy;
        }
        else{
            consulta = consulta + notAcademy;
        }

        //logger.info("consulta : "+consulta);

        List<Course> cursos = new ArrayList<Course>();
        List<SqlRow> sqlRows= Ebean.createSqlQuery(consulta).findList();
        //logger.info("consulta realizada ");
        for(SqlRow row : sqlRows) {
            //logger.info("consulta realizada ");
            cursos.add(new Course(row));
        }

        return cursos;
    }

    public static Course getCourseByCode(String code){
        return find.query().where().eq("code", code).findOne();
    }

    public int getPercentAdvance(Long userId){

        int closedContents = 0;
        int totalContents = 0;
        for(Chapter c: chapters){
            for(Lesson l : c.lessons){
                totalContents = totalContents + l.contents.size();
                closedContents = closedContents + l.getClosedContents(userId);
            }
        }

        if(totalContents>0){
            return (closedContents*100)/totalContents;
        }
        return 0;
    }

    public static Course getCoursebyLesson(Long lessonId){
        Lesson lesson = Lesson.find.byId(lessonId);
        return lesson.chapter.course;
    }

    public static List<Course> getCoursesbyAdmin(){
        return Course.find.all();
    }

    public static List<Course> getCoursesbyName(String name){
        return Course.find.query().where().ilike("name", "%"+name+"%").orderBy("created_date desc").findList();
    }

}

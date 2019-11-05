package models;

import io.ebean.Ebean;
import io.ebean.Finder;
import io.ebean.SqlRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user_course")
public class UserCourse extends BaseModel {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Id
    @SequenceGenerator(name = "generator", sequenceName = "user_course_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    public Course course;

    public Boolean blocked;

    public UserCourse(){}

    public UserCourse(SqlRow row){
        this.id = row.getLong("id");
        this.user = User.find.byId(new Long(row.getString("user_id")));
        this.course = Course.find.byId(new Long(row.getString("course_id")));
        this.blocked = new Boolean(row.getString("blocked"));
    }

    public static Finder<Long,UserCourse> find = new Finder<Long,UserCourse>(UserCourse.class);

    public static List<UserCourse> getMySchoolCourses(Long userId){
        //return find.where().eq("user.id", userId).ne("course.academy.id", null).orderBy("course.id asc").findList();
        return find.query().where().eq("user.id", userId).eq("course.academy.id", null).orderBy("course.id asc").findList();
    }

    public static UserCourse getMySchoolCourseEnroll(Long userId, String codeCourse){
        //logger.info("Curso de escuela");
        //return find.where().eq("user.id", userId).eq("course.code", codeCourse).ne("course.academy.id", null).orderBy("course.id asc").findUnique();
        return find.query().where().eq("user.id", userId).eq("course.code", codeCourse).orderBy("course.id asc").findOne();
    }

    public static UserCourse getMyLibraryCourseEnroll(Long userId, String courseCod){
        return find.query().where().eq("user.id", userId).eq("course.code", courseCod).orderBy("course.id asc").findOne();
    }

    public static List<UserCourse> getStudentsByCourse(Long courseId){
        return UserCourse.find.query().where().eq("course.id",courseId).findList();
    }

    public static List<UserCourse> getAllStudentsbyTeacherId(Long teacherId){
        String consulta ="select uc.id,uc.user_id,uc.course_id,uc.blocked " +
                          "from user_course uc where course_id in (select ci.course_id from course_instructor ci " +
                          "where instructor_id ="+teacherId.toString()+" )";

        List<UserCourse> alumnos = new ArrayList<UserCourse>();
        List<SqlRow> sqlRows= Ebean.createSqlQuery(consulta).findList();

        for(SqlRow row : sqlRows) {
            alumnos.add(new UserCourse(row));
        }
        return alumnos;
     }

    public static void openCourse(Long courseId, Long userId){
        UserCourse uc = UserCourse.find.query().where().eq("course.id", courseId).eq("user.id", userId).findOne();
        uc.blocked = false;
        uc.update();
    }

}
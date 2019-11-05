package models;

import io.ebean.Finder;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.GeneratedValue;
import java.util.*;

@Entity
@Table(name = "course_instructor")
public class CourseInstructor extends BaseModel {

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "course_instructor_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @ManyToOne
    @JoinColumn(name = "instructor_id", insertable = false, updatable = false)
    public User instructor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    public Course course;

    public static Finder<Long, CourseInstructor> find = new Finder<Long, CourseInstructor>(CourseInstructor.class);

    public static List<CourseInstructor> getMyCourses(Long userId){

        return find.query().where().eq("instructor.id", userId).eq("course.academy.id", null).orderBy("course.id asc").findList();
    }

    public static List<CourseInstructor> getMySchoolCourses(Long userId){

        return find.query().where().eq("instructor.id", userId).ne("course.academy.id", null).orderBy("course.id asc").findList();
    }


    public static CourseInstructor getMySchoolCourseDicted(Long userId, Long codeCourse){
        return find.query().where().eq("instructor.id", userId).eq("course.code", codeCourse).orderBy("course.id asc").findOne();
    }


    /*
    public static List<CourseTeacher> getAllClases(){
        Query<CourseTeacher>  query = Ebean.createQuery(CourseTeacher.class);
        List<CourseTeacher> listado= new ArrayList<CourseTeacher>();
        listado = query.findList();
        return listado;
    }

    public static CourseTeacher getClaseXId(String idClase){
        Long id = Long.parseLong(idClase);
        Query<CourseTeacher>  query = Ebean.createQuery(CourseTeacher.class);
        query
        .where()
        .eq("id_clase",id)
        .eq("estado",0);
        return query.findUnique();
    };
    */
}

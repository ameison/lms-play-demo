package models;

import io.ebean.Finder;
import io.ebean.annotation.EnumValue;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "course_library")
public class CourseLibrary extends BaseModel {

    public enum Status {@EnumValue("A") ACTIVE, @EnumValue("I") INACTIVE, }

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "course_library_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @OneToOne
    @Column(unique=true)
    public Course course;

    public Double price;
    public Status state;

    public static Finder<Long, CourseLibrary> find = new Finder<Long, CourseLibrary>(CourseLibrary.class);

    public static List<CourseLibrary> getCoursesOpened(){
        return find.query().where().eq("state", Status.ACTIVE).eq("course.academy", null).orderBy("course.id asc").findList();
    }

    public static CourseLibrary getCourse(String codeCourse){
        return find.query().where().eq("course.code", codeCourse).eq("course.academy", null).eq("state", Status.ACTIVE).findOne();
    }

    public static CourseLibrary getCourseById(Long idCourse){
        return find.query().where().eq("course_id", idCourse).eq("state", Status.ACTIVE).findOne();
    }


}

package models;

import io.ebean.Ebean;
import io.ebean.Finder;
import io.ebean.Query;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "schedule")
public class CourseSchedule extends BaseModel {

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "schedule_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @ManyToOne
    @Column(name = "course_teacher_id")
    public CourseInstructor courseTeacher;

    public String start_time;
    public String finish_time;
    public int state;

    @OneToMany
    public List<ScheduleDetail> listScheduleDetail;

    public static Finder<Long,CourseSchedule> find = new Finder<Long,CourseSchedule>(CourseSchedule.class);

    public static List<CourseSchedule> getSchedulesXIdClass(Long idClass){
        List<CourseSchedule> listSchedules = new ArrayList<CourseSchedule>();
        Query<CourseSchedule> query = Ebean.createQuery(CourseSchedule.class);
        query
                .where()
                .eq("clase_id_clase",idClass)
                .eq("state",0);
        listSchedules = query.findList();
        return listSchedules;
    }

}

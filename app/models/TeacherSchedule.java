package models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.Finder;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "teacher_schedule")
public class TeacherSchedule extends BaseModel {

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "teacher_schedule_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id_shedule;

    @Column(name = "date")
    public String date;

    @Column(name = "start_time")
    public String start_time;

    @Column(name = "end_time")
    public String end_time;

    @JsonIgnore
    @OneToOne
    public User user;

    public static Finder<Long,TeacherSchedule> find = new Finder<>(TeacherSchedule.class);

    public static List<TeacherSchedule> findSchedulebyUser(Long userId){
        List<TeacherSchedule> schedules = TeacherSchedule.find.query().where().eq("user_id", userId).findList();
        return schedules;
    }
}

package models;

import javax.persistence.*;

@Entity
@Table(name = "schedule_detail")
public class ScheduleDetail extends BaseModel {

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "schedule_detail_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @ManyToOne
    public CourseSchedule schedule;

}

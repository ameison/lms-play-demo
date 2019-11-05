package models;

import io.ebean.Finder;
import io.ebean.annotation.EnumValue;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "exercise")
public class Exercise extends BaseModel  {

    public enum Level{@EnumValue("1") easy, @EnumValue("2") medium, @EnumValue("3") hard,}

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "exercise_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @ManyToOne
    public User user;

    @Column(length = 45)
    public String name;

    @Column(columnDefinition = "TEXT")
    public String description;

    @Column
    public Level level;

    //in seconds
    @Column(name="time_limit")
    public String timeLimit;

    @Column(name="source_limit")
    public String sourceLimit;

    public static Finder<Long,Exercise> find = new Finder<Long,Exercise>(Exercise.class);

    public static List<Exercise> getEasyExercises(){
        return find.query().where().eq("level", Level.easy ).orderBy("created_date desc").findList();
    }

    public static List<Exercise> getMediumExercises(){
        return find.query().where().eq("level", Level.medium ).orderBy("created_date  desc").findList();
    }

    public static List<Exercise> getHardExercises(){
        return find.query().where().eq("level", Level.hard).orderBy("created_date  desc").findList();
    }


}

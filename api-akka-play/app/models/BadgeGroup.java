package models;

import io.ebean.Finder;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="badge_group")
public class BadgeGroup extends BaseModel {

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "badge_group_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @Column
    public String name;

    @Column
    public String description;

    public static Finder<Long, BadgeGroup> find = new Finder<>(BadgeGroup.class);

    public static List<BadgeGroup> getAllGroupBadges(){
        List<BadgeGroup> lstGroupBadges = BadgeGroup.find.all();
        return lstGroupBadges;
    }

}

package models;

import io.ebean.Finder;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="user_badges")
public class UserBadge extends BaseModel {

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "user_badges_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @ManyToOne
    public User user;

    @ManyToOne
    public Badge badge;

    public String type;

    @Column(name = "register_date")
    public Date registerDate;

    @Column(name = "modified_date")
    public Date modifiedDate;

    public static Finder<Long, UserBadge> find = new Finder<Long, UserBadge>(UserBadge.class);

    public static List<UserBadge> getBadgesUser(Long userId){
        //Long userId = ApplicationController.getUserIdSession();
        return UserBadge.find.query().where().eq("user_id", userId).findList();
    }

    public static List<UserBadge> getBadgesUser(String type,Long userId){
        //Long userId = ApplicationController.getUserIdSession();
        return UserBadge.find.query().where().eq("user_id", userId).eq("type", type).findList();
    }

}

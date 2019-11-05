package models;

import io.ebean.Finder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="badge")
public class Badge extends BaseModel {

    //ID_BADGES
    public static final long PVIDEO = 4;
    public static final long SVIDEO = 5;
    public static final long TVIDEO = 6;

    public static final long PQUIZ = 7;
    public static final long SQUIZ = 8;
    public static final long TQUIZ = 9;

    public static final long PEXCERCISE = 10;
    public static final long SEXCERCISE = 11;
    public static final long TEXCERCISE = 12;

    public static final long PMAPA = 13;

    //REQUIRED BADGES
    public static final int BRONZE = 1;
    public static final int SILVER = 2;
    public static final int GOLD = 3;

    //TYPE BADGES
    public static final String VIDEO = "2";
    public static final String QUIZ = "3";
    public static final String EXERCISE = "4";
    public static final String MAP = "5";

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "badge_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @OneToMany(mappedBy = "badge")
    public List<UserBadge> userBadge;

    @ManyToOne
    public BadgeGroup badgeGroup;

    @Column
    public String name;

    @Column 
    public String description;

    @Column(name = "url_image")
    public String urlImage;

    public String type;

    public static Finder<Long, Badge> find = new Finder<>(Badge.class);

    public static List<Badge> getAllBadges(){
        return Badge.find.all();
    }

}
package models;

import io.ebean.Finder;
import javax.persistence.*;

@Entity
@Table(name="videos")
public class Video extends BaseModel {

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "videos_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @Column
    public String name;

    @Column
    public String description;

    @Column(name = "url_video")
    public String urlVideo;

    public static Finder<Long, Video> find = new Finder<>(Video.class);

    public Video(){}

    public Video(Long id){ this.id = id; }


}
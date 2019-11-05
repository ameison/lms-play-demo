package models;

import io.ebean.Finder;
import io.ebean.annotation.EnumValue;
import javax.persistence.*;

@Entity
@Table(name = "content_video")
public class ContentVideo extends BaseModel {

    public enum Isolation {@EnumValue("O") OPEN, @EnumValue("C") CLOSE, }
    public enum VideoHost {@EnumValue("W") WISIA, @EnumValue("Y") YOUTUBE, @EnumValue("O") OTROS, }

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "content_video_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @OneToOne
    @Column(unique=true)
    public Content content;

    public Isolation isolation;

    @Column(length = 100)
    public String title;

    @Column(length = 400)
    public String description;

    @Column(length = 400)
    public String tocken;

    public String time;

    public boolean state;

    @Column(name = "url_video", length = 500)
    public String url_video;

    public VideoHost video_host;

    public static Finder<Long, ContentVideo> find = new Finder<Long, ContentVideo>(ContentVideo.class);

    public static ContentVideo getVideo(Long videoId){
        return ContentVideo.find.byId(videoId);
    }

    public static ContentVideo getvidebyidContent(Long contentId){
        return ContentVideo.find.query().where().eq("content_id", contentId).findOne();
    }

}

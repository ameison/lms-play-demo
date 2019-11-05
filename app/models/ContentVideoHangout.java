package models;
import io.ebean.Finder;
import io.ebean.annotation.EnumValue;

import javax.persistence.*;

@Entity
@Table(name = "content_video_hangout")
public class ContentVideoHangout  extends BaseModel{

    public enum ApiGid { @EnumValue("245625370725") VIDEOHANGOUT, }
    public enum Apiurl { @EnumValue("https://plus.google.com/hangouts/_") APIURL, }

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "content_video_hangout_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    //@ManyToOne
    //public UserResource  user;

    public ApiGid  gid;

    @Column(columnDefinition = "TEXT")
    public String  urlHangout;

    @ManyToOne
    public ContentVideo contentvideo;

    public static Finder<Long, ContentVideoHangout> find = new Finder<Long, ContentVideoHangout>(ContentVideoHangout.class);

    public static ContentVideoHangout getChatbyidVideo(Long iVideo){
        ContentVideoHangout cVideo = find.query().where().eq("contentvideo.id", iVideo.toString()).findOne();
        return cVideo;
    }
}

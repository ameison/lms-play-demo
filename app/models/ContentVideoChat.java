package models;

import io.ebean.Finder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "content_video_chat")
public class ContentVideoChat  extends BaseModel {

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "content_video_chat_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @ManyToOne
    public User  user;

    @ManyToOne
    public ContentVideo contentvideo;

    @Column(columnDefinition = "TEXT")
    public String  message;

    public static Finder<Long, ContentVideoChat> find = new Finder<Long, ContentVideoChat>(ContentVideoChat.class);

    public ContentVideoChat() {}

    public static List<ContentVideoChat> getChatbyidVideo(Long iVideo){
        List<ContentVideoChat> listChat = find.query().where().eq("contentvideo.id", iVideo).findList();

    return listChat;
    }

}

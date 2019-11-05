package models;

import io.ebean.Finder;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "content_homework_chat")
public class ContentHomeworkChat extends BaseModel {

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "content_homework_chat_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @ManyToOne
    public User  user;

    @ManyToOne
    public ContentHomework contenthomework;

    @Column(columnDefinition = "TEXT")
    public String  message;

    public static Finder<Long, ContentHomeworkChat> find = new Finder<Long, ContentHomeworkChat>(ContentHomeworkChat.class);

    public ContentHomeworkChat() {}

    public static List<ContentHomeworkChat> getChatbyidHomework(Long iVideo){
        List<ContentHomeworkChat> listChat = find.query().where().eq("contenthomework.id", iVideo.toString()).findList();
        return listChat;
    }
}

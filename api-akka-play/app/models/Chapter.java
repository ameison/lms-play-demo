package models;

import io.ebean.Finder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "chapter")
public class Chapter extends BaseModel{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String INICIADO = "1";
    public static final String TERMINADO = "2";

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "chapter_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @Column(length = 45)
    public String name;

    @Column(length = 200)
    public String imagen;

    @ManyToOne
    public Course course;

    @OneToMany(mappedBy = "chapter")
    public List<Lesson> lessons;

    public Chapter(Long id){
        this.id = id;
    }

    public Chapter(){}

    public static Finder<Long, Chapter> find = new Finder<>(Chapter.class);

    public int getPercentAdvance(Long userId){

        int closedContents = 0;
        int totalContents = 0;
        for(Lesson l : lessons){
            totalContents = totalContents + l.contents.size();
            closedContents = closedContents + l.getClosedContents(userId);
        }

        if(totalContents>0){
            logger.info(((closedContents * 100) / totalContents) +"");
            return (closedContents*100)/totalContents;
        }
        else{
            logger.info("void");
        }
        return 0;
    }

    public static List<Chapter> getChapters(){
        return Chapter.find.all();
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}

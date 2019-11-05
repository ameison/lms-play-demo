package models;

import io.ebean.Finder;

import javax.persistence.*;

@Entity
@Table(name = "content_game")
public class ContentGame extends BaseModel {

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "content_game_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @OneToOne
    @Column(unique=true)
    public Content content;

    @Column(length = 100)
    public String title;

    @Column(length = 400)
    public String description;

    @Column(name = "pos_ini",length = 10)
    public String posIni;

    @Column(name = "orientation",length = 1)
    public String orientation;

    @Column(name = "balls_x",length = 100)
    public String ballsX;

    @Column(name = "balls_y",length = 100)
    public String ballsY;

    @Column(name = "balls_rep",length = 100)
    public String ballsRep;

    @Column(name = "code_example",length = 1200)
    public String codeExample;

    @Column(name = "cor_x_ini",length = 100)
    public String corXIniObs;

    @Column(name = "cor_y_ini",length = 100)
    public String corYIniObs;

    @Column(name = "cor_x_fin",length = 100)
    public String corXFinObs;

    @Column(name = "cor_y_fin",length = 100)
    public String corYFinObs;

    @Column(name = "pos_fin",length = 10)
    public String posFin;

    @Column(name = "orientation_f",length = 1)
    public String orientationFinal;

    @Column(name = "balls_x_f",length = 100)
    public String ballsXFinal;

    @Column(name = "balls_y_f",length = 100)
    public String ballsYFinal;

    @Column(name = "balls_rep_f",length = 100)
    public String ballsRepFinal;

    public static Finder<Long, ContentGame> find = new Finder<Long, ContentGame>(ContentGame.class);

    public static ContentGame getContentGameById(Long id, int typeContent){
        ContentGame contentGame = ContentGame.find.byId(id);

        if(contentGame!=null){
            Content content = contentGame.content;

            if(content.typeContent == typeContent){
                return contentGame;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
    public static ContentGame getContentGameByContentId(Long idContent){
        ContentGame contentGame = ContentGame.find.query().where().eq("content_id",idContent).findOne();
        if(contentGame!=null){
            return contentGame;
        }else{
            return null;
        }
    }


}

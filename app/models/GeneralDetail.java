package models;

import io.ebean.Finder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="general_detail")
public class GeneralDetail extends BaseModel{

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "general_detail_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @Column(name = "general_id")
    public Long generalId;

    public String abbreviation;
    public String description;

    @ManyToOne
    public General general;

    public static Finder<Long,GeneralDetail> find = new Finder<Long,GeneralDetail>(GeneralDetail.class);

    public static List<GeneralDetail> getStatesCountry(long generalId){
        return GeneralDetail.find.query().where().eq("generalId", generalId).findList();
    }

}

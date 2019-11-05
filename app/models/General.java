package models;

import io.ebean.Finder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="general")
public class General extends BaseModel{

    static String TIPORG = "TIPORG";
    static String TIPPLAN = "TIPPLAN";
    static String TIPCOUNTRY = "TIPCOUNTRY";

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "general_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    public String abbreviation;
    public String description;
    public String code;

    @OneToMany(mappedBy = "general")
    public GeneralDetail generalDetail;

    public static Finder<Long,General> find = new Finder<Long,General>(General.class);

    public static List<General> getTypesOrganizations(){
        return General.find.query().where().eq("code",TIPORG).findList();
    }

    public static List<General> getCountries(){
        return General.find.query().where().eq("code",TIPCOUNTRY).findList();
    }

    public static List<General> getTypesPlan(){
        return General.find.query().where().eq("code",TIPPLAN).findList();
    }


}

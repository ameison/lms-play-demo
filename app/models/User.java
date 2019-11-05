package models;

import io.ebean.Ebean;
import io.ebean.Finder;
import io.ebean.SqlRow;
import io.ebean.annotation.EnumValue;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.*;
import java.io.File;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name="users")
public class User extends BaseModel {

    static final Logger logger = LoggerFactory.getLogger("errores");
    public static final String USER_EMAIL = "email";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "username";
    public static final String USER_FULLNAME = "userfullname";
    public static final String USER_COD = "usercod";
    public static final String USER_IMAGE = "userImage";
    public static final String USER_ROL = "userRol";
    public static final String USER_NICK = "userNick";

    public enum Status { @EnumValue("A") ACTIVE, @EnumValue("I") INACTIVE, }
    public enum Rol {@EnumValue("S") STUDENT, @EnumValue("T") TEACHER, @EnumValue("A") ADMINISTRADOR,}

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "user_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @Column(unique=true)
    public Long code;

    @Column(unique=true)
    public String username;

    @Column(name = "first_name")
    public String firstName;

    @Column(name = "last_name")
    public String lastName;

    public String about;
    public Timestamp birthday;

    @Column(unique=true)
    public String email;

    public String password;
    @Column(name = "url_image")
    public String urlImage;

    public File fileImage;

    @Column(name = "class_code")
    public String classCode;

    @Column(name = "organization_name",length = 100)
    public String organizationName;

    @Column(name = "organization_type")
    public int organizationType;

    @Column(name = "country_id",length = 50)
    public String countryId;

    public Status state;

    @Column(name = "telephone",length = 11)
    public String telephone;

    @OneToOne(mappedBy = "user")
    public Payment payment;

    @OneToMany(mappedBy = "user")
    public List<Exercise> listExercises;

    @Column(name = "info_completed")
    public boolean infoCompleted;

    public Rol rol;

    @OneToMany(mappedBy = "user")
    public List<UserBadge> userBadge;

    public static Finder<Long,User> find = new Finder<>(User.class);

    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static User findByEmail(String email){
        return User.find.query().where().eq("email", email).findOne();
    }

    public static User findTeacherByEmail(String email){
        return User.find.query().where().eq("email", email).eq("rol", Rol.TEACHER).findOne();
    }

    public static boolean isAutenticated(String email, String password){
        logger.debug("email "+email);
        User user = User.find.query().select("email").where().eq("email", email).findOne();
        if(user != null){
            logger.debug("user "+user);

            logger.debug("user "+user.password);
            if(BCrypt.checkpw(password, user.password)){
                return true;
            }
        }
        return false;
    }

    public static boolean existEmail(String email){
        User u = User.find.query().select("email").where().eq("email", email).findOne();
        if(u != null){
            return true;
        }
        return false;
    }

    public static boolean existUsername(String username){
        User u = User.find.query().select("username").where().eq("username", username).findOne();
        if(u != null){
            return true;
        }
        return false;
    }

    public static boolean existEmailUpdate(String email,Long id){
        User u = User.find.query().select("email").where().eq("email", email).findOne();
        if((u != null) && !(u.id.equals(id))){
            logger.info(u.id.toString());
            return true;
        }
        return false;
    }

    public static Long getGeneratedCode(){
        String sql = "select max(code) as max_code from users";
        SqlRow a = Ebean.createSqlQuery(sql).findOne();
        return a.getLong("max_code") + 1;
    }

    public static User byCode(String code){
        return find.query().where().eq("code", Long.parseLong(code)).findOne();
    }

}
package models;

import io.ebean.Model;
import io.ebean.annotation.CreatedTimestamp;
import io.ebean.annotation.UpdatedTimestamp;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;


@MappedSuperclass
public class BaseModel extends Model {

    @CreatedTimestamp
    @Column(name = "created_date")
    public Date createdDate;

    @UpdatedTimestamp
    @Column(name = "updated_date")
    public Date updatedDate;

}

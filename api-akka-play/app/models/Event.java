package models;

import io.ebean.Finder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="events")
public class Event extends BaseModel {

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "events_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @Column
    public String name;

    @Column 
    public String description;

    @Column
    public String address;

    @Column
    public Date startDate;

    @Column
    public Date endDate;

    @Column(name = "url_image")
    public String urlImage;

    public static Finder<Long, Event> find = new Finder<>(Event.class);

    public Event(){}

    public Event(Long id){ this.id = id; }


}
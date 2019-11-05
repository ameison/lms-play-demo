package models;


import io.ebean.Finder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "academy")
public class Academy extends BaseModel {

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "academy_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @Column
    public String name;

    @OneToMany(mappedBy = "academy")
    public List<Course> courses;

    public static Finder<Long, Academy> find = new Finder<>(Academy.class);


}


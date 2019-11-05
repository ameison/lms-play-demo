package models;

import io.ebean.Finder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "resource")
public class Resource extends BaseModel{

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "resource_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @ManyToOne
    public Course course;

    @Column(length = 100)
    public String titulo;

    @Column(length = 200)
    public String description;

    @Column(length = 500)
    public String url_image;

    @Column(length = 500)
    public String url_content;


    public static Finder<Long, Resource> find = new Finder<>(Resource.class);



}

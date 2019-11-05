package models;

import io.ebean.Finder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="banners")
public class Banner extends BaseModel {

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "banners_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @Column
    public String name;

    @Column
    public String description;

    @Column(name = "url_image")
    public String urlImage;

    public static Finder<Long, Banner> find = new Finder<>(Banner.class);

    public Banner(){}

    public Banner(Long id){ this.id = id; }

}
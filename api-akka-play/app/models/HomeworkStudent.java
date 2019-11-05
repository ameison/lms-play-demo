package models;

import javax.persistence.*;


@Entity
@Table(name = "homework_student")
public class HomeworkStudent extends BaseModel {

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "homework_student_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @ManyToOne
    public ContentHomework homework;

    @ManyToOne
    public User student;

    @Column(columnDefinition = "TEXT")
    public String descripcion;

    public String urlComplemento;
}

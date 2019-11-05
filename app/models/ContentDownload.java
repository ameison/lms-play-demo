package models;

import io.ebean.Finder;

import javax.persistence.*;

@Entity
@Table(name = "content_download")
public class ContentDownload extends BaseModel {

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "content_download_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @OneToOne
    @Column(unique=true)
    public Content content;

    @Column(length = 100)
    public String title;

    @Column(columnDefinition = "TEXT")
    public String description;

    @Column(name = "url_file")
    public String urlFile;

    public static Finder<Long, ContentDownload> find = new Finder<Long, ContentDownload>(ContentDownload.class);

    public static ContentDownload getDownload(Long downloadId){
        return ContentDownload.find.byId(downloadId);
    }

    public static ContentDownload getDownloadbyidContent(Long contentId){
        return ContentDownload.find.query().where().eq("content_id", contentId).findOne();
    }
}

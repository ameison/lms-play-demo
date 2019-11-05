package forms;

import models.ContentDownload;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

public class ContentDownloadForm {

    public Long id;

    @Required
    @MinLength(value = 4)
    public String title;

    @Required
    @MinLength(value = 10)
    public String description;

    public String urlFile;

    //DATOS DEL CONTENIDO

    public Long idContent;

    @Required
    @MinLength(value = 4)
    public String contentName;

    @Required
    public int typeContent;

    public int orderContent;

    public Long idLesson;

    public String validate() {
        return null;
    }

    public ContentDownloadForm(ContentDownload download){
        this.id = download.id;
        this.title = download.title;
        this.description = download.description;
        this.urlFile = download.urlFile;

        if (download.content != null) {
            this.idContent = download.content.id;
            this.contentName = download.content.name;
            this.typeContent = download.content.typeContent;
            this.orderContent = download.content.orderContent;
            this.idLesson = download.content.lesson.id;
        }

    }

    public ContentDownloadForm(){}

}
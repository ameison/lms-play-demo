package forms;

import models.ContentVideo;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

public class ContentVideoForm {

    public Long id;

    @Required
    @MinLength(value = 4)
    public String title;

    @Required
    @MinLength(value = 10)
    public String description;

    public String urlVideo;

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

    public ContentVideoForm(ContentVideo video){
        this.id = video.id;
        this.title = video.title;
        this.description = video.description;
        this.urlVideo = video.url_video;

        if (video.content != null) {
            this.idContent = video.content.id;
            this.contentName = video.content.name;
            this.typeContent = video.content.typeContent;
            this.orderContent = video.content.orderContent;
            this.idLesson = video.content.lesson.id;
        }

    }

    public ContentVideoForm(){}

}
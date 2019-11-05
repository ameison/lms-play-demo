package forms;

import models.Video;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

public class VideoForm {

    public Long id;

    @Required
    @MinLength(value = 4)
    public String name;

    @Required
    @MinLength(value = 10)
    public String description;

    public String urlVideo;

    public String validate() {
        return null;
    }

    public VideoForm(Video video){
        this.id = video.id;
        this.name = video.name;
        this.description = video.description;
        this.urlVideo = video.urlVideo;
    }

    public VideoForm(){}

}
package forms;

import models.Course;
import models.CourseLibrary;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.i18n.Lang;
import play.i18n.Messages;
import play.i18n.MessagesApi;

import javax.inject.Inject;

public class CourseForm {

    public Long id;
    private MessagesApi messagesApi;

    @Inject
    public CourseForm(MessagesApi messagesApi) {
        this.messagesApi = messagesApi;
    }

    @Required
    @MinLength(value = 4)
    public String code;

    @Required
    @MinLength(value = 4)
    public String name;

    @Required
    @MinLength(value = 10)
    public String description;

    public String urlVideo;

    public boolean courseFree;

    public String urlImage;

    public Double price;

    public String validate() {
        if (name.trim().isEmpty()) {
            return messagesApi.get(Lang.forCode("es"), "admin.course.name");
        }else if(description.trim().isEmpty()){
            return messagesApi.get(Lang.forCode("es"), "admin.course.description");
        }else if(code.trim().isEmpty()){
            return messagesApi.get(Lang.forCode("es"), "admin.course.code");
        }
        return null;
    }

    public CourseForm(Course course, CourseLibrary courseLibrary){
        this.id = course.id;
        this.code = course.code;
        this.name = course.name;
        this.description = course.description;
        this.urlVideo = course.urlVideo;
        this.urlImage = course.urlImage;
        if (course.courseFree != null)
            this.courseFree = course.courseFree;
        if (courseLibrary != null)
            this.price = courseLibrary.price;
    }

    public CourseForm(){}

}
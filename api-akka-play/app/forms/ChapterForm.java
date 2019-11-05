package forms;

import models.Chapter;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.i18n.Lang;
import play.i18n.MessagesApi;
import play.i18n.Messages;

import javax.inject.Inject;

public class ChapterForm {

    private MessagesApi messagesApi;

    @Inject
    public ChapterForm(MessagesApi messagesApi) {
        this.messagesApi = messagesApi;
    }

    public Long id;

    @Required
    @MinLength(value = 6)
    public String name;

    public String imagen;

    public Long idCourse;

    public String validate() {
        if (name.trim().isEmpty()) {
            return messagesApi.get(Lang.forCode("es"), "admin.course.name");
        }
        return null;
    }

    public ChapterForm(Chapter chapter){
        this.id = chapter.id;
        this.name = chapter.name;
        this.imagen= chapter.imagen;
        this.idCourse = chapter.course.id;
    }

    public ChapterForm(){}

}
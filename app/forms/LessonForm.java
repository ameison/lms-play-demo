package forms;

import models.Lesson;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.i18n.Lang;
import play.i18n.Messages;
import play.i18n.MessagesApi;

import javax.inject.Inject;

public class LessonForm {

    public Long id;
    private MessagesApi messagesApi;

    @Inject
    public LessonForm(MessagesApi messagesApi) {
        this.messagesApi = messagesApi;
    }

    @Required
    @MinLength(value = 4)
    public String name;

    @Required
    public int orderLesson;

    public Long idChapter;

    public String validate() {
        if (name.trim().isEmpty()) {
            return messagesApi.get(Lang.forCode("es"), "admin.lesson.name");
        }else if(orderLesson <= 0){
            return messagesApi.get(Lang.forCode("es"), "admin.lesson.orderLesson");
        }
        return null;
    }

    public LessonForm(Lesson lesson){
        this.id = lesson.id;
        this.name = lesson.name;
        this.orderLesson = lesson.orderLesson;
        this.idChapter = lesson.chapter.id;
    }

    public LessonForm(){}

}
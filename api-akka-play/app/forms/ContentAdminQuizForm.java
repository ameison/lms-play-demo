package forms;

import models.ContentQuiz;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

public class ContentAdminQuizForm {

    public Long id;

    @Required
    @MinLength(value = 4)
    public String question;

    //DATOS DEL CONTENIDO PREGUNTA

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

    public ContentAdminQuizForm(ContentQuiz quiz){
        this.id = quiz.id;
        this.question = quiz.question;

        if (quiz.content != null) {
            this.idContent = quiz.content.id;
            this.contentName = quiz.content.name;
            this.typeContent = quiz.content.typeContent;
            this.orderContent = quiz.content.orderContent;
            this.idLesson = quiz.content.lesson.id;
        }
    }

    public ContentAdminQuizForm(){}

}
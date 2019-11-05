package forms;

import models.ContentQuizAnswer;
import models.UserProgress;
import play.data.validation.Constraints;
import play.i18n.Messages;

public class UserQuestionForm {

    @Constraints.Required
    @Constraints.MinLength(value = 6)
    public String title;

    @Constraints.Required
    @Constraints.MinLength(value = 6)
    public String description;

    @Constraints.Required
    public Long courseId;

    public String validate(){
        return null;
    }


}

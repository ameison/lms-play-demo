package forms;

import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

public class ContactForm {

    @Required
    @MinLength(value = 4)
    public String name;

    @Required
    @MinLength(value = 4)
    public String email;

    @Required
    @MinLength(value = 4)
    public String subject;

    @Required
    @MinLength(value = 10)
    public String message;

    public String validate() {
        return null;
    }

    public ContactForm(){}

}
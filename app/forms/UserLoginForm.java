package forms;

import play.data.validation.Constraints;
import play.data.validation.Constraints.Validate;
import play.data.validation.Constraints.Validatable;
import play.data.validation.ValidationError;
import java.util.ArrayList;
import java.util.List;

public class UserLoginForm  {

    @Constraints.Required
    @Constraints.Email
    @Constraints.MinLength(value = 6)
    public String email;

    @Constraints.Required
    @Constraints.MinLength(value = 6)
    public String password;

}

/*
@Validate
public class UserLoginForm implements Validatable<List<ValidationError>>{

    @Constraints.Required
    @Constraints.Email
    @Constraints.MinLength(value = 6)
    public String email;

    @Constraints.Required
    @Constraints.MinLength(value = 6)
    public String password;

    @Override
    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();
        if (email.trim().isEmpty()) {
            errors.add(new ValidationError("email", "website.login.mensajeerror"));
        }
        if (password.trim().isEmpty()) {
            errors.add(new ValidationError("password", "website.login.mensajeerror"));
        }
        return errors.isEmpty() ? null : errors;
    }

}*/

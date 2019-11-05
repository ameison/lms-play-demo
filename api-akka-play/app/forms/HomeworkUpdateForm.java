package forms;

import org.hibernate.validator.constraints.URL;
import play.data.validation.Constraints;

public class HomeworkUpdateForm {

    public Long id;

    public Long idUsuario;
    @Constraints.MinLength(value = 5)
    public String description;
    @URL
    public String urlComplemento;
}

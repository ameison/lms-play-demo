package forms;

import models.User;
import org.hibernate.validator.constraints.Email;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.i18n.Lang;
import play.i18n.Messages;
import play.i18n.MessagesApi;
import util.Constants;

import javax.inject.Inject;


public class StudentRegisterForm {

    private MessagesApi messagesApi;

    @Inject
    public StudentRegisterForm(MessagesApi messagesApi) {
        this.messagesApi = messagesApi;
    }

    @Required
    @MinLength(value = 2)
    public String firstName;
    @Required
    @MinLength(value = 2)
    public String lastName;

    @Required
    @MinLength(value = 2)
    public String username;

    @Required
    @Email
    public String email;

    @Required
    @MinLength(value = 6)
    public String password;

    @Required
    @MinLength(value = 6)
    public String repeatPassword;

    //Datos del plan adquirido
    public String planId;
    public String cardNumber;
    public String cardCvc;
    public String cardMonth;
    public String cardYear;

    public String terms;

    public String validate() {
        if (User.existEmail(email)) {
            return messagesApi.get(Lang.forCode("es"), "website.login.emailerror");
        }else if(!password.equals(repeatPassword)){
            return messagesApi.get(Lang.forCode("es"), "website.login.passworderror");
        }else if(terms == null){
            return messagesApi.get(Lang.forCode("es"), "website.login.terms");
        }else if(User.existUsername(username)){
            return "el usuario ya existe";
        }


        /*
        if(!planId.equals(""+ Payment.FREE)){
            if (!validateCardNumber().equals(Constants.OK)){return validateCardNumber();}
            if (!validateCardCvc().equals(Constants.OK)){return validateCardCvc();}
            if (Integer.parseInt(cardMonth) == 0){return "debe seleccionar un mes";}
            if (!validateCardYear().equals(Constants.OK)){return validateCardYear();}
        }
        */
        //saveStudent();
        return null;
    }
/*
    public void saveStudent(){
        UserResource user = new UserResource();
        user.code = UserResource.getGeneratedCode();
        user.firstName = firstName;
        user.lastName = lastName;
        user.email = email;
        user.password = BCrypt.hashpw(password, BCrypt.gensalt());
        user.rol = UserResource.Rol.STUDENT;
        user.save();

    }
*/
    public String validateCardNumber(){
        if(cardNumber.trim().isEmpty()){
            return "el número de tarjeta se encuentra vacia";
        }else if(cardNumber.length() < 16){
            return "el número de tarjeta es de 16 dígitos";
        }
        return Constants.OK;
    }

    public String validateCardCvc(){
        if(cardCvc.trim().isEmpty()){
            return "el número del Cvc se encuentra vacio";
        }else if(cardCvc.length() < 3){
            return "el número del Cvc es de 3 dígitos";
        }
        return Constants.OK;
    }

    public String validateCardYear(){
        if(cardYear.trim().isEmpty()){
            return "el año se encuentra vacio";
        }else if(cardYear.length() < 3){
            return "el año tiene 4 dígitos";
        }
        return Constants.OK;
    }

    @Override
    public String toString() {
        return "IndividualRegister{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", repeatPassword='" + repeatPassword + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardCvc='" + cardCvc + '\'' +
                ", cardMonth='" + cardMonth + '\'' +
                ", cardYear='" + cardYear + '\'' +
                ", planId='" + planId + '\'' +
                ", terms='" + terms + '\'' +
                '}';
    }

}
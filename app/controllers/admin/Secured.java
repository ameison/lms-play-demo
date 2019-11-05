package controllers.admin;

import controllers.student.routes;
import play.mvc.*;
import play.mvc.Http.*;

import java.util.Optional;


public class Secured extends Security.Authenticator {

    @Override
    public Optional getUsername(Http.Request ctx) {
        return ctx.session().getOptional("email");
    }

    @Override
    public Result onUnauthorized(Http.Request ctx) {
        return redirect(routes.WebsiteController.login()).flashing("danger",  "You need to login before access the application.");
    }
}

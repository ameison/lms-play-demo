package controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.typesafe.config.Config;
import controllers.admin.Invoice;
import controllers.admin.Secured;
import forms.UserLoginForm;
import jwt.JwtControllerHelper;
import jwt.VerifiedJwt;
import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.ValidationError;
import play.i18n.MessagesApi;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
import repository.UserRepository;
import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Json;

public class SecuredController extends Controller {

    private final FormFactory formFactory;
    private final UserRepository userRepository;
    private final HttpExecutionContext httpExecutionContext;
    private final MessagesApi messagesApi;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JwtControllerHelper jwtControllerHelper;
    private Config config;

    @Inject
    public SecuredController(FormFactory formFactory,
                             UserRepository userRepository,
                             HttpExecutionContext httpExecutionContext,
                             MessagesApi messagesApi,
                             JwtControllerHelper jwtControllerHelper,
                             Config config) {
        this.formFactory = formFactory;
        this.userRepository = userRepository;
        this.httpExecutionContext = httpExecutionContext;
        this.messagesApi = messagesApi;
        this.jwtControllerHelper = jwtControllerHelper;
        this.config = config;
    }

    public Result login2(Http.Request request) throws UnsupportedEncodingException {
        JsonNode body = request.body().asJson();

        if (body == null) {
            logger.error("json body is null");
            return forbidden();
        }

        if (body.hasNonNull("username") && body.hasNonNull("password") && body.get("username").asText().equals("abc")) {
            ObjectNode result = Json.newObject();
            result.put("access_token", getSignedToken(7l));
            return ok(result);
        } else {
            logger.error("json body not as expected: {}", body.toString());
        }

        return forbidden();
    }

    public Result jsonMap(){
        HashMap<String, Object> result = new HashMap<String, Object>(){
            {
                put("str", "String");
                put("int", 123);
            }
        };
        return ok(Json.toJson(result));
    }

    public Result jsonObject(){
        Invoice invoice = new Invoice("Perico de los Palotes", "City", "123456-7", "002245",
                LocalDate.now(), new BigDecimal(1293));
        return ok(Json.toJson(invoice));
    }

    public Result jsonCatch(Http.Request request){

        JsonNode jsonNode = request.body().asJson();
        if(jsonNode == null){
            return badRequest("Expecting Json data");
        }else{
            Invoice invoice = Json.fromJson(jsonNode, Invoice.class);
            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return ok(invoice.getCode() + " | " + invoice.getIdNumber() +
                    " | " + f.format(invoice.getDate()));
        }

    }


    public Result index(Http.Request request) {
        List<Course> courses = Course.find.query().orderBy("created_date desc").setMaxRows(9).findList();
        List<Event> events = Event.find.query().orderBy("created_date desc").setMaxRows(6).findList();
        List<Banner> banners = Banner.find.query().orderBy("created_date desc").findList();
        return ok(views.html.student.website.index.render(courses, events, banners, request, messagesApi.preferred(request)));
    }

    public Result loginForm(Http.Request request) {
        logger.debug("Cargando login form...");
        if(request.session().getOptional(User.USER_EMAIL).isPresent()){
            List<Course> courses = Course.find.query().orderBy("created_date desc").setMaxRows(3).findList();
            List<Event> events = Event.find.query().orderBy("created_date desc").setMaxRows(6).findList();
            List<Banner> banners = Banner.find.query().orderBy("created_date desc").findList();
            return ok(views.html.student.website.index.render(courses, events, banners, request, messagesApi.preferred(request)));
        }
        Form<UserLoginForm> loginForm = formFactory.form(UserLoginForm.class);
        return ok(views.html.student.website.loginForm.render(loginForm, request, messagesApi.preferred(request)));
    }

    public CompletionStage<Result> login(Http.Request request) {

        Form<UserLoginForm> loginForm = formFactory.form(UserLoginForm.class).bindFromRequest(request);

        if (loginForm.hasErrors()) {
            logger.debug("Cargando nuevamente login form");
            return CompletableFuture.completedFuture(badRequest(views.html.student.website.loginForm.render(loginForm, request, messagesApi.preferred(request))));
        }else {
            logger.debug("Paso la validacion de campos obligatorios");
            String email = loginForm.get().email;
            String password = loginForm.get().password;

            return userRepository.isAutenticated(email,password).thenApplyAsync(userOptional -> {
                logger.debug("isAutenticated >> isPresent ... "+ userOptional.isPresent());
                if(userOptional.isPresent()){
                    User user = userOptional.get();
                    logger.debug("UserResource autenticated >> ... "+ user.email);

                    switch(User.Rol.valueOf(user.rol.name())){
                        case ADMINISTRADOR:

                            return redirect("/admin/courses")
                                    .addingToSession(request, User.USER_EMAIL, user.email)
                                    .addingToSession(request, User.USER_ID, user.id.toString())
                                    .addingToSession(request, User.USER_NAME, user.firstName)
                                    .addingToSession(request, User.USER_COD, user.code.toString())
                                    .addingToSession(request, User.USER_NICK, user.username)
                                    .addingToSession(request, User.USER_ROL, user.rol.name());
                        default:
                            return redirect("/school/courses")
                                    .addingToSession(request, User.USER_EMAIL, user.email)
                                    .addingToSession(request, User.USER_ID, user.id.toString())
                                    .addingToSession(request, User.USER_NAME, user.firstName)
                                    .addingToSession(request, User.USER_COD, user.code.toString())
                                    .addingToSession(request, User.USER_NICK, user.username)
                                    .addingToSession(request, User.USER_ROL, user.rol.name());
                    }
                }else{
                    logger.debug("No autentico");
                    List<ValidationError> errors = new ArrayList<>();
                    errors.add(new ValidationError("password", "website.login.mensajeerror"));
                    return ok(views.html.student.website.loginForm.render(loginForm, request, messagesApi.preferred(request)));
                }

            }, httpExecutionContext.current());
        }
    }



    @Security.Authenticated(Secured.class)
    public Result logout(Http.Request request) {
        Form<UserLoginForm> loginForm = formFactory.form(UserLoginForm.class);
        return ok(views.html.student.website.loginForm.render(loginForm, request, messagesApi.preferred(request))).
                removingFromSession(request, User.USER_EMAIL, User.USER_ID, User.USER_NAME, User.USER_COD, User.USER_NICK, User.USER_ROL );

    }

    /******************************************************
    /******************************************************
    /*****************************************************/
    private String getSignedToken(Long userId) throws UnsupportedEncodingException {
        String secret = config.getString("play.http.secret.key");

        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("ThePlayApp")
                .withClaim("user_id", userId)
                .withExpiresAt(Date.from(ZonedDateTime.now(ZoneId.systemDefault()).plusMinutes(10).toInstant()))
                .sign(algorithm);
    }

    public Result requiresJwt(Http.Request request) {
        return jwtControllerHelper.verify(request, res -> {
            if (res.left.isPresent()) {
                return forbidden(res.left.get().toString());
            }

            VerifiedJwt verifiedJwt = res.right.get();
            logger.debug("{}", verifiedJwt);

            ObjectNode result = Json.newObject();
            result.put("access", "granted");
            result.put("secret_data", "birds fly");
            return ok(result);
        });
    }

}

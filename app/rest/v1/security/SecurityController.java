package rest.v1.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.config.Config;
import jwt.JwtControllerHelper;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
import repository.UserRepository;
import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.CompletionStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resources.UserResource;

public class SecurityController extends Controller {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;
    private final HttpExecutionContext httpExecutionContext;
    private final PostResourceHandler handler;
    private final JwtControllerHelper jwtControllerHelper;
    private final Config config;

    @Inject
    public SecurityController(
                             UserRepository userRepository,
                             HttpExecutionContext httpExecutionContext,
                             PostResourceHandler handler,
                             JwtControllerHelper jwtControllerHelper,
                             Config config) {
        this.userRepository = userRepository;
        this.httpExecutionContext = httpExecutionContext;
        this.handler = handler;
        this.jwtControllerHelper = jwtControllerHelper;
        this.config = config;
    }

    public CompletionStage<Result> login(Http.Request request) throws UnsupportedEncodingException{
        JsonNode json = request.body().asJson();
        UserResource resource = Json.fromJson(json, UserResource.class);
        return handler.login(request, resource).thenApplyAsync(userLogin -> {
            return userLogin.map(userResource ->
                    ok(Json.toJson(userResource))
            ).orElseGet(Results::notFound);
        }, httpExecutionContext.current());
    }

}

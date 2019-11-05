package rest.v1.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.palominolabs.http.url.UrlBuilder;
import com.typesafe.config.Config;
import controllers.admin.Invoice;
import forms.UserLoginForm;
import jwt.JwtControllerHelper;
import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.ValidationError;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Http;
import play.mvc.Result;
import repository.UserRepository;
import resources.UserResource;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.nio.charset.CharacterCodingException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

/**
 * Handles presentation of Post resources, which map to JSON.
 */
public class PostResourceHandler {

    //private final PostRepository repository;

    private final FormFactory formFactory;
    private final UserRepository userRepository;
    private final HttpExecutionContext httpExecutionContext;
    private final MessagesApi messagesApi;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JwtControllerHelper jwtControllerHelper;
    private Config config;

    @Inject
    public PostResourceHandler(FormFactory formFactory,
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
/*
    @Inject
    public PostResourceHandler(PostRepository repository, HttpExecutionContext ec) {
        this.repository = repository;
        this.ec = ec;
    }
*/
    public CompletionStage<Optional<UserResource>> login(Http.Request request, UserResource us) throws UnsupportedEncodingException{
        String g = getSignedToken(new Long("2"));

        return userRepository.isAutenticated(us.getEmail(), us.getPassword()).thenApplyAsync(userOptional -> {
            logger.debug("isAutenticated >> isPresent ... "+ userOptional.isPresent());
            if(userOptional.isPresent()){
                User user = userOptional.get();
                logger.debug("serResource autenticated >> ... "+ user.email);

            }else{
                logger.debug("No autentico");

            }

            return userOptional.map(myUser -> new UserResource(myUser.firstName, myUser.password, g));
        }, httpExecutionContext.current());

    }

    private String getSignedToken(Long userId) throws UnsupportedEncodingException {
        String secret = config.getString("play.http.secret.key");

        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("ThePlayApp")
                .withClaim("user_id", userId)
                .withExpiresAt(Date.from(ZonedDateTime.now(ZoneId.systemDefault()).plusMinutes(10).toInstant()))
                .sign(algorithm);
    }

/*
    public CompletionStage<Stream<PostResource>> find(Http.Request request) {
        return repository.list().thenApplyAsync(postDataStream -> {
            return postDataStream.map(data -> new PostResource(data, link(request, data)));
        }, ec.current());
    }

    public CompletionStage<PostResource> create(Http.Request request, PostResource resource) {
        final PostData data = new PostData(resource.getTitle(), resource.getBody());
        return repository.create(data).thenApplyAsync(savedData -> {
            return new PostResource(savedData, link(request, savedData));
        }, ec.current());
    }

    public CompletionStage<Optional<PostResource>> lookup(Http.Request request,String id) {
        return repository.get(Long.parseLong(id)).thenApplyAsync(optionalData -> {
            return optionalData.map(data -> new PostResource(data, link(request, data)));
        }, ec.current());
    }

    public CompletionStage<Optional<PostResource>> update(Http.Request request,String id, PostResource resource) {
        final PostData data = new PostData(resource.getTitle(), resource.getBody());
        return repository.update(Long.parseLong(id), data).thenApplyAsync(optionalData -> {
            return optionalData.map(op -> new PostResource(op, link(request, op)));
        }, ec.current());
    }
*/
}
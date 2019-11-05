package rest.v1.security;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.admin.Invoice;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
import resources.UserResource;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@With(PostAction.class)
public class PostController extends Controller {

    private HttpExecutionContext ec;
    private PostResourceHandler handler;

    @Inject
    public PostController(HttpExecutionContext ec, PostResourceHandler handler) {
        this.ec = ec;
        this.handler = handler;
    }

    public CompletionStage<Result> login(Http.Request request) throws UnsupportedEncodingException {
        JsonNode json = request.body().asJson();
        UserResource resource = Json.fromJson(json, UserResource.class);
        return handler.login(request, resource).thenApplyAsync(userLogin -> {
            return userLogin.map(userResource ->
                    ok(Json.toJson(userResource))
            ).orElseGet(Results::notFound);
        }, ec.current());
    }
/*
    public CompletionStage<Result> list(Http.Request request) {
        return handler.find(request).thenApplyAsync(posts -> {
            final List<PostResource> postList = posts.collect(Collectors.toList());
            return ok(Json.toJson(postList));
        }, ec.current());
    }

    public CompletionStage<Result> show(Http.Request request, String id) {
        return handler.lookup(request, id).thenApplyAsync(optionalResource -> {
            return optionalResource.map(resource ->
                ok(Json.toJson(resource))
            ).orElseGet(Results::notFound);
        }, ec.current());
    }

    public CompletionStage<Result> update(Http.Request request, String id) {
        JsonNode json = request.body().asJson();
        PostResource resource = Json.fromJson(json, PostResource.class);
        return handler.update(request, id, resource).thenApplyAsync(optionalResource -> {
            return optionalResource.map(r ->
                    ok(Json.toJson(r))
            ).orElseGet(Results::notFound
            );
        }, ec.current());
    }

    public CompletionStage<Result> create(Http.Request request) {
        JsonNode json = request.body().asJson();
        final PostResource resource = Json.fromJson(json, PostResource.class);
        return handler.create(request, resource).thenApplyAsync(savedResource -> {
            return created(Json.toJson(savedResource));
        }, ec.current());
    }*/
}
package repository;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import io.ebean.PagedList;
import io.ebean.Transaction;
import models.User;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.db.ebean.EbeanConfig;
import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import static java.util.concurrent.CompletableFuture.supplyAsync;

public class UserRepository {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EbeanServer ebeanServer;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public UserRepository(EbeanConfig ebeanConfig, DatabaseExecutionContext executionContext) {
        this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
        this.executionContext = executionContext;
    }

    public CompletionStage<Optional<User>> getById(Long id) {
        return supplyAsync(() -> Optional.ofNullable(ebeanServer.find(User.class).setId(id).findOne()), executionContext);
    }

    /*public CompletionStage<Optional<UserResource>> findByEmail(String email) {
        return supplyAsync(() -> Optional.ofNullable(ebeanServer.find(UserResource.class)
                .where()
                .eq("email", email)
                .findOne()), executionContext);
    }*/

    public CompletionStage<Optional<User>> isAutenticated(String email, String password){

        return supplyAsync(() -> {
            Transaction txn = ebeanServer.beginTransaction();
            Optional<User> value = Optional.empty();
            try {
                User user = ebeanServer.find(User.class).where().eq("email", email).findOne();
                if(user != null){
                    logger.debug("duser "+user);
                    logger.info("iuser "+user);
                    if(BCrypt.checkpw(password, user.password)){
                        value = Optional.of(user);
                    }
                }else{
                    logger.info("iuser es null");
                }

            } finally {
                txn.end();
            }
            return value;
        }, executionContext);

    }

    /**
     * Return a paged list of users
     *
     * @param page     Page to display
     * @param pageSize Number of computers per page
     * @param sortBy   Computer property used for sorting
     * @param order    Sort order (either or asc or desc)
     * @param filter   Filter applied on the name column
     */
    public CompletionStage<PagedList<User>> pageList(int page, int pageSize, String sortBy, String order, String filter) {
        return supplyAsync(() ->
                ebeanServer.find(User.class).where()
                        .ilike("name", "%" + filter + "%")
                        .orderBy(sortBy + " " + order)
                        //.fetch("company")
                        .setFirstRow(page * pageSize)
                        .setMaxRows(pageSize)
                        .findPagedList(), executionContext);
    }
}

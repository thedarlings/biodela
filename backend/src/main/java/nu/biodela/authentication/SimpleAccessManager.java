package nu.biodela.authentication;

import static io.javalin.ApiBuilder.path;
import static io.javalin.ApiBuilder.post;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.javalin.Context;
import io.javalin.Handler;
import io.javalin.security.AccessManager;
import io.javalin.security.Role;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import nu.biodela.Service;
import nu.biodela.authentication.session.SessionStore;
import nu.biodela.user.User;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

@Singleton
public class SimpleAccessManager implements AccessManager, Service {
  private static final String AUTH_TOKEN_PARAM_NAME = "SessionToken";
  private final SessionStore sessions;
  private final Gson gson;
  private final Logger logger;

  @Inject
  public SimpleAccessManager(SessionStore sessions, Gson gson, ILoggerFactory loggerFactory) {
    this.sessions = sessions;
    this.gson = gson;
    this.logger = loggerFactory.getLogger(SimpleAccessManager.class.getName());
  }

  @Override
  public void manage(Handler handler, Context ctx, List<Role> permittedRoles) throws Exception {
    Optional<User> user = getAuthenticatedUser(ctx);
    if (user.isPresent() || permittedRoles.contains(ApiRole.ANYONE)) {
      handler.handle(ctx);
    } else {
      ctx.status(401).result(gson.toJson("Unauthorized"));
    }
  }

  private Optional<User> getAuthenticatedUser(Context ctx) {
    return Optional.ofNullable(ctx.queryParam(AUTH_TOKEN_PARAM_NAME))
        .flatMap(sessions::getActiveUser);
  }

  private boolean authenticateUser(User user) {
    //TODO: Add authentication
    return true;
  }

  @Override
  public void setUpRoutes() {
    path("auth", () -> post(this::onLoginRequest));
  }

  private void onLoginRequest(Context context) {
    try {
      User user = gson.fromJson(context.body(), User.class);
      if (authenticateUser(user)) {
        logger.info("Logging in " + user.getUsername());
        String sessionId = sessions.createSession(user);
        context
            .contentType("json")
            .status(200)
            .result("{\""+ AUTH_TOKEN_PARAM_NAME + "\":\"" + sessionId + "\"}");
      } else {
        logger.info("Got unauthorized login attempt");
        context.status(403)
            .contentType("json")
            .result(gson.toJson("Unauthorized!"));
      }
    } catch (JsonSyntaxException e) {
      logger.info("Got json syntax error");
      context
          .status(400)
          .contentType("json")
          .result(gson.toJson("Json syntax error: " + e.getLocalizedMessage()));
    }
  }
}

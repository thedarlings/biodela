package nu.biodela.authentication;

import static io.javalin.ApiBuilder.path;
import static io.javalin.ApiBuilder.post;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.javalin.Context;
import io.javalin.Handler;
import io.javalin.security.AccessManager;
import io.javalin.security.Role;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import nu.biodela.Service;
import nu.biodela.user.User;

@Singleton
public class SimpleAccessManager implements AccessManager, Service {
  public static final String AUTH_TOKEN_PARAM_NAME = "SessionToken";
  private final Map<String, User> sessions;
  private final Gson gson;

  @Inject
  public SimpleAccessManager(Gson gson) {
    this.gson = gson;
    sessions = new HashMap<>();
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
        .map(sessions::get);
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
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, user);
        context.status(200)
            .result("{\""+ AUTH_TOKEN_PARAM_NAME + ":\"" + sessionId + "\"}");
      } else {
        context.status(403)
            .result(gson.toJson("Unauthorized!"));
      }
    } catch (JsonSyntaxException e) {
      context
          .status(400)
          .result(gson.toJson("Json syntax error: " + e.getLocalizedMessage()));
    }
  }
}

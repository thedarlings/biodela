package nu.biodela.authentication.session;

import static io.javalin.ApiBuilder.delete;
import static io.javalin.ApiBuilder.get;
import static io.javalin.ApiBuilder.path;

import com.google.gson.Gson;
import io.javalin.Context;
import java.util.Optional;
import javax.inject.Inject;
import nu.biodela.Service;
import nu.biodela.user.User;

public class SessionService implements Service {
  private static final String SESSION_TOKEN = "SessionToken";
  private final SessionStore sessionStore;
  private final Gson gson;

  @Inject
  public SessionService(SessionStore sessionStore, Gson gson) {
    this.sessionStore = sessionStore;
    this.gson = gson;
  }

  @Override
  public void setUpRoutes() {
    path("auth/sessiontoken/:" + SESSION_TOKEN, () -> {
      get(this::getSession);
      delete(this::removeSession);
    });
  }

  private void removeSession(Context context) {
    String sessionToken = context.param(SESSION_TOKEN);
    boolean success = sessionStore.dropSession(sessionToken);
    if (success) {
      context.status(200).result("Deleted");
    } else {
      context.status(400).result("Not found");
    }
  }

  private void getSession(Context context) {
    String sessionToken = context.param(SESSION_TOKEN);
    Optional<User> activeUser = sessionStore.getActiveUser(sessionToken);
    if (activeUser.isPresent()) {
      User user = activeUser.get();
      user.dropPassword();
      context.status(200).contentType("application/json").result(gson.toJson(user));
    } else {
      context.status(403);
    }
  }
}

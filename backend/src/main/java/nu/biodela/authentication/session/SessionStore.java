package nu.biodela.authentication.session;

import io.javalin.Context;
import java.util.Optional;
import nu.biodela.user.User;

public interface SessionStore {
  Optional<User> getActiveUser(String sessionToken);
  Optional<User> getActiveUser(Context sessionToken);
  String createSession(User user);
}

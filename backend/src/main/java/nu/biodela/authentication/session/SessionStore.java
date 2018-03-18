package nu.biodela.authentication.session;

import io.javalin.Context;
import java.util.Optional;

public interface SessionStore {
  Optional<Long> getActiveUser(String sessionToken);
  Optional<Long> getActiveUser(Context sessionToken);
  String createSession(long userId);
  boolean dropSession(String sessionToken);
}

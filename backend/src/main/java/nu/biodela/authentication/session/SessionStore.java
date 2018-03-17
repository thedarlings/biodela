package nu.biodela.authentication.session;

import java.util.Optional;
import nu.biodela.user.User;

public interface SessionStore {
  Optional<User> getActiveUser(String sessionToken);
  String createSession(User user);
}

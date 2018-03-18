package nu.biodela.authentication.session;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import io.javalin.Context;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Singleton;
import nu.biodela.time.TimeProvider;
import nu.biodela.user.User;

public class InMemorySessionStore implements SessionStore {

  private final TimeProvider time;
  private final long sessionTime;
  private final Map<String, Session> sessions;

  @AutoFactory
  InMemorySessionStore(@Provided TimeProvider time, long sessionTime) {
    this.time = time;
    this.sessionTime = sessionTime;
    sessions = new HashMap<>();
  }


  @Override
  public Optional<User> getActiveUser(String sessionToken) {
    Session session = sessions.get(sessionToken);
    if (session == null) {
      return Optional.empty();
    } else if (time.now().isAfter(session.getExpirationTime())) {
      sessions.remove(sessionToken);
      return Optional.empty();
    } else {
      return Optional.of(session.getUser());
    }
  }

  @Override
  public Optional<User> getActiveUser(Context context) {
    return Optional.ofNullable(context.queryParam("SessionToken"))
        .flatMap(this::getActiveUser);
  }

  @Override
  public String createSession(User user) {
    String sessionId = UUID.randomUUID().toString();
    Session session = new Session(
        sessionId,
        user,
        time.now().plusNanos(sessionTime * 24 * 60 * 60 * 1_000_000_000));
    sessions.put(sessionId, session);
    return sessionId;
  }


  private class Session {
    private final String sessionId;
    private final User user;
    private final Instant expirationTime;

    Session(String sessionId, User user, Instant expirationTime) {
      this.sessionId = sessionId;
      this.user = user;
      this.expirationTime = expirationTime;
    }

    String getSessionId() {
      return sessionId;
    }

    User getUser() {
      return user;
    }

    Instant getExpirationTime() {
      return expirationTime;
    }
  }
}

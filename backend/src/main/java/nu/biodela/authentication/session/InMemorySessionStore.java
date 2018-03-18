package nu.biodela.authentication.session;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import io.javalin.Context;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import nu.biodela.time.TimeProvider;

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
  public Optional<Long> getActiveUser(String sessionToken) {
    Session session = sessions.get(sessionToken);
    if (session == null) {
      return Optional.empty();
    } else if (time.now().isAfter(session.getExpirationTime())) {
      sessions.remove(sessionToken);
      return Optional.empty();
    } else {
      return Optional.of(session.getUserId());
    }
  }

  @Override
  public Optional<Long> getActiveUser(Context context) {
    return Optional.ofNullable(context.queryParam("sessiontoken"))
        .flatMap(this::getActiveUser);
  }

  @Override
  public String createSession(long userId) {
    String sessionId = UUID.randomUUID().toString();
    Session session = new Session(
        sessionId,
        userId,
        time.now().plusNanos(sessionTime * 24 * 60 * 60 * 1_000_000_000));
    sessions.put(sessionId, session);
    return sessionId;
  }

  @Override
  public boolean dropSession(String sessionToken) {
    Session removed = sessions.remove(sessionToken);
    return (removed != null);
  }


  private class Session {
    private final String sessionId;
    private final long userId;
    private final Instant expirationTime;

    Session(String sessionId, long userId, Instant expirationTime) {
      this.sessionId = sessionId;
      this.userId = userId;
      this.expirationTime = expirationTime;
    }

    String getSessionId() {
      return sessionId;
    }

    long getUserId() {
      return userId;
    }

    Instant getExpirationTime() {
      return expirationTime;
    }
  }
}

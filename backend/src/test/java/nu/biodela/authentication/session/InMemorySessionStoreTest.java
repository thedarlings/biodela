package nu.biodela.authentication.session;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.Optional;
import nu.biodela.time.TimeProvider;
import nu.biodela.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InMemorySessionStoreTest {

  @Mock
  private TimeProvider time;
  private InMemorySessionStore target;
  private User user = new User("Pello", "password", "");

  @Before
  public void setUp() throws Exception {
    target = new InMemorySessionStore(time, 1);
    when(time.now()).thenReturn(Instant.now());
  }

  @Test
  public void getActiveUser() {
    // Given no active user
    // When
    Optional<User> activeUser = target.getActiveUser("asd-asd-asd-asdasd-asd");
    // Then
    assertFalse(activeUser.isPresent());
  }

  @Test
  public void createSession() {
    // Given
    User user2 = new User("Olle", "password", "");
    // When
    String session1 = target.createSession(user);
    String session2 = target.createSession(user2);
    // Then
    assertNotEquals(session1, session2);
  }

  @Test
  public void createAndCheckSession() {
    // Given
    String session = target.createSession(user);
    // When
    Optional<User> activeUser = target.getActiveUser(session);
    // Then
    assertTrue(activeUser.isPresent());
    assertEquals(user, activeUser.get());
  }

  @Test
  public void createAndCheckSessionAfterExpiration() {
    // Given
    String session = target.createSession(user);
    // When
    when(time.now()).thenReturn(Instant.now().plus(Duration.ofDays(2)));
    Optional<User> activeUser = target.getActiveUser(session);
    // Then
    assertFalse(activeUser.isPresent());
  }
}
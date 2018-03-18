package nu.biodela.authentication.session;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import nu.biodela.time.TimeProvider;
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
  private Long user = 1L;

  @Before
  public void setUp() {
    target = new InMemorySessionStore(time, 1);
    when(time.now()).thenReturn(Instant.now());
  }

  @Test
  public void getActiveUser() {
    // Given no active user
    // When
    Optional<Long> activeUser = target.getActiveUser("asd-asd-asd-asdasd-asd");
    // Then
    assertFalse(activeUser.isPresent());
  }

  @Test
  public void createSession() {
    // Given
    Long user2 = 2L;
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
    Optional<Long> activeUser = target.getActiveUser(session);
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
    Optional<Long> activeUser = target.getActiveUser(session);
    // Then
    assertFalse(activeUser.isPresent());
  }
}
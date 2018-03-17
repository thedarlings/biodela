package nu.biodela.time;

import java.time.Instant;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TimeProvider {
  @Inject
  public TimeProvider() {
  }
  public Instant now() {
    return Instant.now();
  }
}

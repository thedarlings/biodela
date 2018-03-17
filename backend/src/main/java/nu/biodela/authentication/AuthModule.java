package nu.biodela.authentication;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import io.javalin.security.AccessManager;
import javax.inject.Singleton;
import nu.biodela.Service;
import nu.biodela.authentication.session.InMemorySessionStoreFactory;
import nu.biodela.authentication.session.SessionStore;
import nu.biodela.time.TimeProvider;

@Module
public class AuthModule {
  private final long sessionSurvivalTimeInDays;

  public AuthModule(long sessionSurvivalTimeInDays) {
    this.sessionSurvivalTimeInDays = sessionSurvivalTimeInDays;
  }

  @Singleton
  @Provides
  AccessManager provideAccessManager(SimpleAccessManager impl) {
    return impl;
  }

  @Singleton
  @Provides
  @IntoSet
  Service providesAuthService(SimpleAccessManager impl) {
    return impl;
  }

  @Provides
  SessionStore providesSessionStore(InMemorySessionStoreFactory factory) {
    return factory.create(sessionSurvivalTimeInDays);
  }
}

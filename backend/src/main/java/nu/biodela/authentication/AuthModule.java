package nu.biodela.authentication;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import io.javalin.security.AccessManager;
import javax.inject.Singleton;
import nu.biodela.Service;

@Module
public class AuthModule {
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
}

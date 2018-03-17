package nu.biodela.user;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import nu.biodela.Service;

@Module
public class UserModule {
  @Provides
  @IntoSet
  Service providesUserService(UserService impl) {
    return impl;
  };
}

package nu.biodela.user;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import nu.biodela.Service;

@Module
public abstract class UserModule {
  @Binds
  @IntoSet
  abstract Service providesUserService(UserService impl);

  @Binds
  abstract UserDao providesDao(SqlUserDao impl);
}

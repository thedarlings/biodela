package nu.biodela.user;

import com.google.gson.Gson;

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

  @Provides
  Gson providesGson(){ return new Gson();}
}

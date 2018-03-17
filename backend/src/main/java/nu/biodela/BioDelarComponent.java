package nu.biodela;

import dagger.Component;
import nu.biodela.user.UserModule;
import javax.inject.Singleton;
import nu.biodela.authentication.AuthModule;

@Singleton
@Component(modules = {
    ServerModule.class,
    UserModule.class,
    AuthModule.class
})
public interface BioDelarComponent {
  Main getApp();
}

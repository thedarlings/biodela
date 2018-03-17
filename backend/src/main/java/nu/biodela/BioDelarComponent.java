package nu.biodela;

import dagger.Component;
import nu.biodela.db.DbModule;
import nu.biodela.user.UserModule;
import javax.inject.Singleton;
import nu.biodela.authentication.AuthModule;

@Singleton
@Component(modules = {
    ServerModule.class,
    UserModule.class,
    AuthModule.class,
    DbModule.class
})
public interface BioDelarComponent {
  Main getApp();
}

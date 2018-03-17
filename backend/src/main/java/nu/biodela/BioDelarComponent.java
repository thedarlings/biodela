package nu.biodela;

import dagger.Component;
import nu.biodela.user.UserModule;

@Component(modules = {
    ServerModule.class,
    UserModule.class
})
public interface BioDelarComponent {
  Main getApp();
}

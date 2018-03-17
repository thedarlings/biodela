package nu.biodela;

import dagger.Component;

@Component(modules = ServerModule.class)
public interface BioDelarComponent {
  Main getApp();
}

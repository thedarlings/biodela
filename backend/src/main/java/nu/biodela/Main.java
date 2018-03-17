package nu.biodela;

import static io.javalin.ApiBuilder.path;

import io.javalin.Javalin;
import java.util.Set;
import javax.inject.Inject;

public class Main {
  private final String prefix;
  private final Set<Service> services;
  private final Javalin javalinServer;

  @Inject
  Main(Set<Service> services, String prefix, Javalin javalinServer) {
    this.prefix = prefix;
    this.services = services;
    this.javalinServer = javalinServer;
  }

  private void startServerAndWait() {
    javalinServer.start();
    javalinServer.routes(() ->
      path(prefix, () -> {
        for (Service service : services) {
          service.setUpRoutes();
        }
      }));
  }

  public static void main(String[] args) {
    ServerModule serverModule = new ServerModule(
        "api",
        "public/",
        8080);
    Main app = DaggerBioDelarComponent.builder()
        .serverModule(serverModule)
        .build()
        .getApp();
    app.startServerAndWait();
  }
}

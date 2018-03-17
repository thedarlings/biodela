package nu.biodela;

import static io.javalin.ApiBuilder.path;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import io.javalin.Javalin;
import io.javalin.security.AccessManager;
import java.util.Set;
import nu.biodela.authentication.AuthModule;

public class Main {
  private final String prefix;
  private final Set<Service> services;
  private final Javalin javalinServer;
  private final AccessManager accessManager;

  @AutoFactory
  Main(
      @Provided  Set<Service> services,
      @Provided Javalin javalinServer,
      @Provided AccessManager accessManager,
      String prefix) {
    this.prefix = prefix;
    this.services = services;
    this.javalinServer = javalinServer;
    this.accessManager = accessManager;
  }

  private void startServerAndWait() {
    javalinServer
        .accessManager(accessManager)
        .error(404, ctx -> ctx.redirect("https://http.cat/404"))
        .start();
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
    AuthModule authModule = new AuthModule(14);
    Main app = DaggerBioDelarComponent.builder()
        .serverModule(serverModule)
        .authModule(authModule)
        .build()
        .getApp();
    app.startServerAndWait();
  }
}

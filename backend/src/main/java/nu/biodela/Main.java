package nu.biodela;

import static io.javalin.ApiBuilder.path;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import io.javalin.Javalin;
import io.javalin.security.AccessManager;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Set;
import nu.biodela.authentication.AuthModule;
import nu.biodela.db.DbModule;
import org.jetbrains.annotations.NotNull;

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

  public static void main(String[] args) throws URISyntaxException {
    ServerModule serverModule = getServerModule();
    AuthModule authModule = new AuthModule(14);
    DbModule dbModule = getDbModule();
    Main app = DaggerBioDelarComponent.builder()
        .serverModule(serverModule)
        .authModule(authModule)
        .dbModule(dbModule)
        .build()
        .getApp();
    app.startServerAndWait();
  }

  @NotNull
  private static DbModule getDbModule() throws URISyntaxException {
    Optional<String> databaseUrl = Optional.ofNullable(System.getenv("DATABASE_URL"));
    URI dbUri;
    String dbUsername;
    String dbPassword;
    if (databaseUrl.isPresent()) {
      dbUri = new URI(databaseUrl.get());
      dbUsername = dbUri.getUserInfo().split(":")[0];
      dbPassword = dbUri.getUserInfo().split(":")[1];
    } else {
      dbUri = new URI("postgres://localhost:5432/biodela");
      dbUsername = "postgres";
      dbPassword = "";
    }
    return new DbModule(
        "postgresql",
        dbUri.getHost(),
        String.valueOf(dbUri.getPort()),
        dbUri.getPath(),
        dbUsername,
        dbPassword);
  }

  @NotNull
  private static ServerModule getServerModule() {
    int port = Optional.ofNullable(System.getenv("PORT"))
        .map(Integer::valueOf)
        .orElse(8080);
    return new ServerModule(
        "api",
        "public/",
        port);
  }
}

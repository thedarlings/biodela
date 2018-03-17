package nu.biodela;

import io.javalin.ApiBuilder;
import io.javalin.Javalin;
import java.util.List;
import javax.inject.Inject;

public class Main {

  private final int port;
  private final String staticFilePath;
  private final String prefix;
  private final List<Service> services;

  @Inject
  public Main(int port, String staticFilePath, String prefix, List<Service> services) {
    this.port = port;
    this.staticFilePath = staticFilePath;
    this.prefix = prefix;
    this.services = services;
  }

  private void startServerAndWait() {
    Javalin app = Javalin.create()
        .enableStaticFiles(staticFilePath)
        .enableStandardRequestLogging()
        .port(port)
        .start();

    app.routes(() ->
      ApiBuilder.path(prefix, () -> {
        for (Service service : services) {
          service.setUpRoutes(app);
        }
      }));
  }

  public static void main(String[] args) {
    Main app = DaggerBioDelarComponent.create().getApp();
    app.startServerAndWait();
  }
}

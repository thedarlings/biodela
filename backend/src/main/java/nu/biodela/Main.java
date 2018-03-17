package nu.biodela;

import io.javalin.Javalin;
import javax.inject.Inject;

public class Main {

  @Inject
  public Main() {
  }

  private void startServerAndWait() {
    Javalin app = Javalin.start(8080);
    app.get("/", ctx -> ctx.result("Hello world!"));
  }

  public static void main(String[] args) {
    Main app = DaggerBioDelarComponent.create().getApp();
    app.startServerAndWait();
  }
}

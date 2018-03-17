package nu.biodela;

import io.javalin.Javalin;

public interface Service {
  /**
   * Set up routes for service.
   * @param app The main Javalin app.
   */
  void setUpRoutes(Javalin app);
}

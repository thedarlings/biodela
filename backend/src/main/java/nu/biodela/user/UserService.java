package nu.biodela.user;

import static io.javalin.ApiBuilder.delete;
import static io.javalin.ApiBuilder.get;
import static io.javalin.ApiBuilder.patch;
import static io.javalin.ApiBuilder.path;
import static io.javalin.ApiBuilder.post;

import javax.inject.Inject;
import nu.biodela.Service;

public class UserService implements Service {
  private final UserController userController;

  @Inject
  public UserService(UserController userController) {
    this.userController = userController;
  }

  @Override
  public void setUpRoutes() {
    path("users", () -> {
      get(userController::getAllUsers);
      post(userController::createUser);
      path(":id", () -> {
        get(userController::getUser);
        patch(userController::updateUser);
        delete(userController::deleteUser);
      });
    });
  }

}

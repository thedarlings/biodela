package nu.biodela.user;

import static io.javalin.ApiBuilder.delete;
import static io.javalin.ApiBuilder.get;
import static io.javalin.ApiBuilder.patch;
import static io.javalin.ApiBuilder.path;
import static io.javalin.ApiBuilder.post;
import static io.javalin.security.Role.roles;

import javax.inject.Inject;
import nu.biodela.Service;
import nu.biodela.authentication.ApiRole;

public class UserService implements Service {
  private final UserController userController;

  @Inject
  UserService(UserController userController) {
    this.userController = userController;
  }

  @Override
  public void setUpRoutes() {
    path("users", () -> {
      get(userController::getAllUsers, roles(ApiRole.USER));
      post(userController::createUser, roles(ApiRole.ANYONE));
      path(":id", () -> {
        get(userController::getUser, roles(ApiRole.USER));
        patch(userController::updateUser, roles(ApiRole.USER));
        delete(userController::deleteUser, roles(ApiRole.USER));
      });
    });
  }

}

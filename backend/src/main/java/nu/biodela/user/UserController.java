package nu.biodela.user;

import com.google.gson.Gson;
import io.javalin.Context;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;

public class UserController {

  private Gson gson;
  private final UserDao userDao;

  @Inject
  UserController(Gson gson, UserDao userDao) {
    this.gson = gson;
    this.userDao = userDao;
  }

  public void getAllUsers(Context context) {
    final List<User> all = userDao.findAll();
    String json = gson.toJson(all);
    context.status(200);
    context.result(json);
  }

  public void createUser(Context context) {
    String body = context.body();
    User user = gson.fromJson(body, User.class);
    user.setId(3);
    context.result("User added");
    context.status(200);
  }

  public void getUser(Context context) {
    Optional<User> optUser = Optional.ofNullable(context.param("id"))
        .map(Long::valueOf)
        .flatMap(userDao::findById);

    if (optUser.isPresent()) {
      User user = optUser.get();
      user.dropPassword();
      context.status(200)
          .contentType("application/json")
          .result(gson.toJson(user));
    } else {
      context
          .status(400)
          .result("Invalid request syntax: no valid parameter to search for user with");

    }
  }

  public void updateUser(Context context) {

  }

  public void deleteUser(Context context) {
  }
}

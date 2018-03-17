package nu.biodela.user;

import com.google.gson.Gson;
import io.javalin.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

public class UserController {

  private Gson gson;
  private final List<User> allUsers;
  private final SqlUserDao sqlUserDao;

  @Inject
  public UserController(Gson gson, SqlUserDao sqlUserDao) {
    this.gson = gson;
    this.sqlUserDao = sqlUserDao;
    allUsers = new ArrayList<>();
    init();
  }

  private void init() {
    allUsers.add(new User(1, "Rebecca", "helloRebecca", "rebecca.hellstrom@gmail.com"));
    allUsers.add(new User(2, "David", "helloDavid", "david@nowhere.com"));
  }

  public void getAllUsers(Context context) {
    final List<User> all = sqlUserDao.findAll();
    String json = gson.toJson(all);
    context.status(200);
    context.result(json);
  }

  public void createUser(Context context) {
    String body = context.body();
    User user = gson.fromJson(body, User.class);
    user.setId(3);
    allUsers.add(user);
    context.result("User added");
    context.status(200);
  }

  public void getUser(Context context) {
    final Map<String, String> paramMap = context.paramMap();
    List<User> users;
    if (paramMap.containsKey("id")) {
      String id = paramMap.get("id");
      users = sqlUserDao.findById(id);
      context.result(gson.toJson(users));
    } else if (paramMap.containsKey("username")) {
      String username = paramMap.get("username");
      users = sqlUserDao.findByUsername(username);
      context.result(gson.toJson(users));
    } else {
      //fixme context.status() failed user doesn't exist
    }

  }

  public void updateUser(Context context) {

  }

  public void deleteUser(Context context) {
  }
}

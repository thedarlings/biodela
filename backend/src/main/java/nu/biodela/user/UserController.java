package nu.biodela.user;

import com.google.gson.Gson;
import io.javalin.Context;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserController {

  private Gson gson;
  private final List<User> allUsers;
  private final SqlUserDao sqlUserDao;
  private final Logger logger;

  @Inject
  public UserController(Gson gson, SqlUserDao sqlUserDao) {
    this.gson = gson;
    this.sqlUserDao = sqlUserDao;
    this.logger = LoggerFactory.getLogger(UserController.class.getName());
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
    List<User> users = null;
    final String param = context.param("id");
    if (param != null) {
      users = sqlUserDao.findById(param);
    }
    final Map<String, String> paramMap = context.paramMap();
    context.result(gson.toJson(users));

    List<String> keys = Arrays.asList("id", "username");
    for (String key : keys) {
      if (paramMap.containsKey(key)) {
        String value = paramMap.get(key);
        users = (key.equals("id")) ? sqlUserDao.findById(key) : sqlUserDao.findByUsername(key);
        if (users == null){
          logger.warn("No users were could be found for " + key + ": " + value);
          return;
        }
        context.result(gson.toJson(users));
      }
    }

    if (users == null ) {
      context.status(400);
      context.result("Invalid request syntax: no valid parameter to search for user with");
    }

  }

  public void updateUser(Context context) {

  }

  public void deleteUser(Context context) {
  }
}

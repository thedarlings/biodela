package nu.biodela.user;



import com.google.gson.Gson;
import io.javalin.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;

public class UserController {

  private Gson gson;
  private final List<User> allUsers;

  @Inject
  public UserController(Gson gson) {
    this.gson = gson;
    allUsers = new ArrayList<>();
    init();
  }

  private void init() {
    allUsers.add(new User("Rebecca", "helloRebecca", "rebecca.hellstrom@gmail.com"));
    allUsers.add(new User("David", "helloDavid", "david@nowhere.com"));
  }

  public void getAllUsers(Context context) {
    context.status(200);
    context.result("HEJ USER");

  }

  public void createUser(Context context) {
    String body = context.body();
    User user = gson.fromJson(body, User.class);
    user.setId(UUID.randomUUID());
    allUsers.add(user);
    context.result("User added");
    context.status(200);
  }

  public void getUser(Context context) {

  }

  public void updateUser(Context context) {

  }

  public void deleteUser(Context context) {
  }
}

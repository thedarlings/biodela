package nu.biodela.user;

import java.util.List;
import java.util.Optional;

public interface UserDao {

  List<User> findAll();
  Optional<User> findById(long id);
  Optional<User> findByUsername(String username);
  boolean insertEmployee(User user);
  boolean updateEmployee(User user);
  boolean deleteEmployee(User user);

}

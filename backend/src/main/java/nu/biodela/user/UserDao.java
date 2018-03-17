package nu.biodela.user;

import java.util.List;

public interface UserDao {

  List<User> findAll();
  List<User> findById(String id);
  List<User> findByUsername(String username);
  boolean insertEmployee(User user);
  boolean updateEmployee(User user);
  boolean deleteEmployee(User user);

}

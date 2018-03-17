package nu.biodela.user;

import java.util.List;

public interface UserDAO {

  List<User> findAll();
  List<User> findById();
  List<User> findByUsername();
  boolean insertEmployee(User user);
  boolean updateEmployee(User user);
  boolean deleteEmployee(User user);

}

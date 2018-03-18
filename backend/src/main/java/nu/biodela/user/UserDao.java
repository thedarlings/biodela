package nu.biodela.user;

import java.util.List;
import java.util.Optional;

public interface UserDao {

  List<User> findAll();
  Optional<User> findById(long id);
  Optional<User> findByUsername(String username);
  boolean insert(User user);
  boolean update(User user);
  boolean delete(User user);

}

package nu.biodela.user;

import java.util.List;

public class SqlUserDao implements UserDao {

  public SqlUserDao() {
  }

  @Override
  public List<User> findAll() {
    return null;
  }

  @Override
  public List<User> findById() {
    return null;
  }

  @Override
  public List<User> findByUsername() {
    return null;
  }

  @Override
  public boolean insertEmployee(User user) {
    return false;
  }

  @Override
  public boolean updateEmployee(User user) {
    return false;
  }

  @Override
  public boolean deleteEmployee(User user) {
    return false;
  }
}

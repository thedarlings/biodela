package nu.biodela.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import nu.biodela.db.DbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlUserDao implements UserDao {

  private static final String USER_ID = "user_id";
  private static final String USERNAME = "user_name";
  private static final String PASSWORD = "password";
  private static final String EMAIL = "email";
  private DbService dbService;
  private final Logger logger;

  @Inject
  public SqlUserDao(DbService dbService) {
    this.dbService = dbService;
    this.logger = LoggerFactory.getLogger(SqlUserDao.class);
  }


  @Override
  public List<User> findAll() {
    String selectTableSQL = "SELECT * from users";
    return getUsers(selectTableSQL);
  }

  @Override
  public Optional<User> findById(long id) {
    String selectTableSQL = String.format("SELECT * from users WHERE %s=%s", USER_ID, id);
    List<User> users = getUsers(selectTableSQL);
    if (users.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.ofNullable(users.get(0));
    }
  }

  @Override
  public Optional<User> findByUsername(String username) {
    String selectTableSQL = String.format("SELECT * from users WHERE %s='%s'", USERNAME, username);
    List<User> users = getUsers(selectTableSQL);
    if (users.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.ofNullable(users.get(0));
    }
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

  private List<User> getUsers(String selectTableSQL) {
    try(Connection dbConnection = dbService.connect();
        Statement statement = dbConnection.createStatement()) {
      logger.info("SQL QUERY TEST: " + selectTableSQL);
      // execute select SQL stetement
      try (ResultSet rs = statement.executeQuery(selectTableSQL)) {
        return userResultSetToList(rs);
      }

    } catch (SQLException e) {
      logger.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

  private List<User> userResultSetToList(ResultSet rs) throws SQLException {
    List<User> allUsers = new ArrayList<>();
    while (rs.next()) {
      int userid = rs.getInt(USER_ID);
      String username = rs.getString(USERNAME);
      String password = rs.getString(PASSWORD);
      String email = rs.getString(EMAIL);

      allUsers.add(new User(userid, username, password, email));
    }
    return allUsers;
  }
}

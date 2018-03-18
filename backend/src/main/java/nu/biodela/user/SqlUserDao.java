package nu.biodela.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nu.biodela.db.DbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlUserDao implements UserDao {

  public static final String USER_ID = "USER_ID";
  public static final String USERNAME = "USER_NAME";
  public static final String PASSWORD = "PASSWORD";
  public static final String EMAIL = "EMAIL";
  private DbService dbService;
  private final Logger logger;

  @Inject
  public SqlUserDao(DbService dbService) {
    this.dbService = dbService;
    this.logger = LoggerFactory.getLogger(SqlUserDao.class);
  }


  @Override
  public List<User> findAll() {

    Connection dbConnection = null;
    Statement statement = null;

    String selectTableSQL = "SELECT * from users";

    return getUsers(dbConnection, statement, selectTableSQL);

  }

  @Override
  public List<User> findById(String id) {
    Connection dbConnection = null;
    Statement statement = null;

    String selectTableSQL = String.format("SELECT * from users WHERE %s=%s", USER_ID, id);

    return getUsers(dbConnection, statement, selectTableSQL);
  }

  @Override
  public List<User> findByUsername(String username) {
    Connection dbConnection = null;
    Statement statement = null;

    String selectTableSQL = String.format("SELECT * from users WHERE %s=%s", USERNAME, username);

    return getUsers(dbConnection, statement, selectTableSQL);
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

  private List<User> getUsers(Connection dbConnection, Statement statement, String selectTableSQL) {
    try {
      dbConnection = dbService.connect();
      statement = dbConnection.createStatement();
      logger.info("SQL QUERY TEST: " + selectTableSQL);

      // execute select SQL stetement
      try (ResultSet rs = statement.executeQuery(selectTableSQL)) {
        return userResultSetToList(rs);
      }

    } catch (SQLException e) {
      logger.error(e.getMessage(), e);
      throw new RuntimeException(e);

    } finally {
      closeStatement(statement);
      closeDbConnection(dbConnection);
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

  private void closeDbConnection(Connection dbConnection) {
    if (dbConnection != null) {
      try {
        dbConnection.close();
      } catch (SQLException e) {
        logger.error(e.getMessage(), e);
      }
    }
  }

  private void closeStatement(Statement statement) {
    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException e) {
        logger.error(e.getMessage(), e);
      }
    }
  }

}

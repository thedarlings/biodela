package nu.biodela.db;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

public class DbService {

  private String username;
  private String password;
  private String dbms;
  private String portNumber;
  private String serverName;
  private String database;
  private Logger logger;

  @AutoFactory
  DbService(
      String username,
      String password,
      String dbms,
      String serverName,
      String portNumber,
      String database,
      @Provided ILoggerFactory loggerFactory) {
    this.dbms = dbms;
    this.portNumber = portNumber;
    this.serverName = serverName;
    this.database = database;
    this.username = username;
    this.password = password;
    this.logger = loggerFactory.getLogger(DbService.class.getName());
  }

  public Connection connect() throws SQLException {
    String url =
        "jdbc:" + dbms + "://" + serverName + ":" + portNumber + database;
    logger.info("Connecting to database: " + url);
    Connection conn = DriverManager.getConnection(
        url,
        username, password);
    return conn;
  }


}

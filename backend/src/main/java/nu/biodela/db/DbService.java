package nu.biodela.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbService {

  private Connection connection;
  private String username;
  private String password;
  private String dbms;
  private String portNumber;
  private String serverName;
  private String database;

  public DbService(String username, String password, String dbms, String serverName,
      String portNumber, String database) {
    this.dbms = dbms;
    this.portNumber = portNumber;
    this.serverName = serverName;
    this.database = database;
    this.username = username;
    this.password = password;
  }

  public Connection getConnection() {
    return connection;
  }

  public Connection connect() throws SQLException {

    Connection conn = null;
    Properties connectionProps = new Properties();
    connectionProps.put("user", this.username);
    connectionProps.put("password", this.password);

    if (this.dbms.equals("postgresql")) {
      conn = DriverManager.getConnection(
          "jdbc:" + this.dbms + "://" +
              this.serverName + ":" + this.portNumber + "/"
              + this.database, connectionProps);
    }
    System.out.println("Connected to database");
    this.connection = conn;
    return conn;
  }


}

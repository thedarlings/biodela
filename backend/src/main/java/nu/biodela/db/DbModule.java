package nu.biodela.db;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

  private final String dbms;
  private final String dbServerName;
  private final String dbPort;
  private final String dbPath;
  private final String username;
  private final String password;


  public DbModule(String dbms, String dbServerName, String dbPort,
                  String dbPath, String username, String password) {
    this.dbms = dbms;
    this.dbServerName = dbServerName;
    this.dbPort = dbPort;
    this.dbPath = dbPath;
    this.username = username;
    this.password = password;
  }

  @Provides
  DbService providesDbService(DbServiceFactory factory) {
    return factory.create(
        username,
        password,
        dbms,
        dbServerName,
        dbPort,
        dbPath);
  }


}

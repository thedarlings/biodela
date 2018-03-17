package nu.biodela.db;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

  private final String DBMS;
  private final String DB_SERVER_NAME;
  private final String DB_PORT_NUMBER;
  private final String DB_NAME;


  public DbModule(String dbms, String db_server_name, String db_port_number,
      String db_name) {
    DBMS = dbms;
    DB_SERVER_NAME = db_server_name;
    DB_PORT_NUMBER = db_port_number;
    DB_NAME = db_name;
  }

  @Provides
  DbService providesDbService() {
    return new DbService("postgres", "", DBMS, DB_SERVER_NAME, DB_PORT_NUMBER, DB_NAME);
  }


}

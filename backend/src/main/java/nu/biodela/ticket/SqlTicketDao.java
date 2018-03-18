package nu.biodela.ticket;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import nu.biodela.db.DbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlTicketDao implements TicketDao {

  private String TICKET_ID = "ticket_id";
  private String CODE = "code";
  private String EXPIRY_DATE = "expiry_date";
  private String CREATED_AT = "created_at";
  private String PROVIDER = "provider";
  private String OWNER_ID = "user_id";
  private String USED = "used";
  private DbService dbService;
  private final Logger logger;

  @Inject
  SqlTicketDao(DbService dbService) {
    this.dbService = dbService;
    this.logger = LoggerFactory.getLogger(SqlTicketDao.class);
  }

  @Override
  public List<Ticket> getAllTickets(long ownerId) {

    String selectTableSQL = "SELECT * FROM tickets WHERE " + OWNER_ID + "='" + ownerId + "'";

    return sqlQueryForTickets(selectTableSQL);
  }


  @Override
  public List<Ticket> getAllTickets() {
    return null;
  }

  @Override
  public boolean update(Ticket ticket) {
    return false;
  }

  @Override
  public boolean insert(Ticket ticket) {
    return false;
  }

  private List<Ticket> sqlQueryForTickets(String selectTableSQL) {
    Connection dbConnection = null;
    Statement statement = null;

    try {
      dbConnection = dbService.connect();
      statement = dbConnection.createStatement();
      logger.debug("SQL QUERY: " + selectTableSQL);

      // execute select SQL stetement
      try (ResultSet rs = statement.executeQuery(selectTableSQL)) {
        return ticketResultSetToList(rs);
      }

    } catch (SQLException e) {
      logger.error(e.getMessage(), e);
      throw new RuntimeException(e);

    } finally {
      closeStatement(statement);
      closeDbConnection(dbConnection);
    }
  }

  private List<Ticket> ticketResultSetToList(ResultSet rs) throws SQLException {
    List<Ticket> tickets = new ArrayList<>();
    while (rs.next()) {

      long id = rs.getInt(TICKET_ID);
      String code = rs.getString(CODE);
      Date expireDate = rs.getDate(EXPIRY_DATE);
      Date entryDate = rs.getDate(CREATED_AT);
      long provider = Long.valueOf(rs.getString(PROVIDER));
      long owner = Long.valueOf(rs.getString(OWNER_ID));
      Boolean used = rs.getBoolean(USED);

      tickets.add(new Ticket(id, code, expireDate, entryDate, provider, owner, used));

    }
    return tickets;
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

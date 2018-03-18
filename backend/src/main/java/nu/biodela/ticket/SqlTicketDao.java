package nu.biodela.ticket;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import nu.biodela.db.DbService;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

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
  SqlTicketDao(DbService dbService, ILoggerFactory loggerFactory) {
    this.dbService = dbService;
    this.logger = loggerFactory.getLogger(SqlTicketDao.class.getName());
  }

  @Override
  public List<Ticket> getAllTickets(long ownerId) {
    String sql = "SELECT * FROM tickets WHERE " + OWNER_ID + "='" + ownerId + "'";
    return getTickets(sql);
  }


  @Override
  public List<Ticket> getAllTickets() {
    String sql = "SELECT * FROM tickets";
    return getTickets(sql);
  }

  @Override
  public boolean update(Ticket ticket) {

    String sql = "UPDATE tickets SET " +
        CODE + "=\'" + ticket.getCode() + "\', " +
        EXPIRY_DATE + "=\'" + ticket.getExpiryDate() + "\', " +
        PROVIDER + "=" + ticket.getProvider();
    if (ticket.getOwnerId().isPresent()) {
        sql += ", " + OWNER_ID + "=" + ticket.getOwnerId().get();
    }

    sql += " WHERE " + TICKET_ID + "=" + ticket.getTicketId();
    return updateTickets(sql);

  }

  @Override
  public boolean insert(Ticket ticket) {
    SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd");
    String sql = "INSERT into tickets (" +
        PROVIDER + ", " +
        CODE + ", " +
        EXPIRY_DATE;
    if (ticket.getOwnerId().isPresent()) {
      sql += ", " + OWNER_ID ;
    }

    sql += ") " +
        " VALUES(" +
        ticket.getProvider() + ", " +
        "\'" + ticket.getCode() + "\', " +
        "\'" + dt.format(ticket.getExpiryDate()) + "\'";

    if (ticket.getOwnerId().isPresent()) {
      sql += ", " + ticket.getOwnerId().get();
    }

    sql += ")";

    return updateTickets(sql);
  }

  private boolean updateTickets(String sql) {
    try (Connection dbConnection = dbService.connect();
        Statement statement = dbConnection.createStatement()) {
      logger.info("Running SQL query: " + sql);
      statement.executeUpdate(sql);
      return true;
    } catch (SQLException e) {
      logger.error(e.getMessage(), e);
      return false;
    }
  }

  private List<Ticket> getTickets(String query) { ;
    try (Connection dbConnection = dbService.connect();
    Statement statement = dbConnection.createStatement()){
      logger.debug("SQL QUERY: " + query);

      // execute select SQL stetement
      try (ResultSet rs = statement.executeQuery(query)) {
        return ticketResultSetToList(rs);
      }

    } catch (SQLException e) {
      logger.error(e.getMessage(), e);
      return Collections.emptyList();

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
      String owner = rs.getString(OWNER_ID);
      Long ownerId = owner != null? Long.valueOf(owner) : null;
      Boolean used = rs.getBoolean(USED);

      tickets.add(new Ticket(id, code, expireDate, entryDate, provider, ownerId, used));

    }
    return tickets;
  }

}

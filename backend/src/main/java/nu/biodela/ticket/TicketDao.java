package nu.biodela.ticket;

import java.util.List;

public interface TicketDao {
  List<Ticket> getAllTickets(long ownerId);
  List<Ticket> getAllTickets();
  boolean update(Ticket ticket);
  boolean insert(Ticket ticket);
}

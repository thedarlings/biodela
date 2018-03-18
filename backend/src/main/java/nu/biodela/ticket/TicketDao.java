package nu.biodela.ticket;

import java.util.List;

public interface TicketDao {
  List<Ticket> getAllTickets(long ownerId);
  Ticket getTicket(long id);
  boolean insertTicket(Ticket ticket);
}

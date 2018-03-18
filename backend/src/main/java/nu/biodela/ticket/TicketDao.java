package nu.biodela.ticket;

import java.util.List;

public interface TicketDao {
  List<Ticket> getAllTickets(long userId);
}

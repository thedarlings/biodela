package nu.biodela.ticket;

import static io.javalin.ApiBuilder.get;
import static io.javalin.ApiBuilder.path;

import com.google.gson.Gson;
import io.javalin.Context;
import io.javalin.security.Role;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import nu.biodela.Service;
import nu.biodela.authentication.ApiRole;
import nu.biodela.authentication.session.SessionStore;

public class TicketService implements Service {
  private final TicketDao dao;
  private final SessionStore sessionStore;
  private final Gson gson;

  @Inject
  public TicketService(TicketDao dao, SessionStore sessionStore, Gson gson) {
    this.dao = dao;
    this.sessionStore = sessionStore;
    this.gson = gson;
  }

  @Override
  public void setUpRoutes() {
    path("tickets", () -> {
      get(this::getAllTickets, Role.roles(ApiRole.USER));
    });
  }

  void getAllTickets(Context context) {
    Optional<List<Ticket>> tickets = sessionStore.getActiveUser(context)
        .map(dao::getAllTickets);
    if (tickets.isPresent()) {
      context
          .status(200)
          .contentType("application/json")
          .result(gson.toJson(tickets));
    }
  }
}

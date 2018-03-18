package nu.biodela.ticket;

import static io.javalin.ApiBuilder.get;
import static io.javalin.ApiBuilder.path;
import static io.javalin.ApiBuilder.post;

import com.google.gson.Gson;
import io.javalin.Context;
import io.javalin.security.Role;
import java.util.Collections;
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
  TicketService(TicketDao dao, SessionStore sessionStore, Gson gson) {
    this.dao = dao;
    this.sessionStore = sessionStore;
    this.gson = gson;
  }

  @Override
  public void setUpRoutes() {
    path("tickets", () -> {
      get(this::getAllTickets, Role.roles(ApiRole.ANYONE));
      post(this::addTicket, Role.roles(ApiRole.USER));
    });
  }

  private void addTicket(Context context) {
    final Optional<Long> userId = sessionStore.getActiveUser(context);

  }

  private void getAllTickets(Context context) {
    Optional<List<Ticket>> tickets = sessionStore.getActiveUser(context)
        .map(dao::getAllTickets);
    context.status(200)
          .contentType("application/json")
          .result(gson.toJson(Collections.emptyList()));
    tickets.ifPresent(tickets1 -> context
        .result(gson.toJson(tickets1)));
  }
}

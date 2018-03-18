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
      get(this::getAllTickets, Role.roles(ApiRole.USER));
      post(this::addTicket, Role.roles(ApiRole.USER));
      get("claim", this::claimTicket, Role.roles(ApiRole.USER));
    });
  }

  private void claimTicket(Context context) {
    Optional<Long> optUserId = sessionStore.getActiveUser(context);
    context.status(200).result(gson.toJson("Non found"));
    optUserId
        .flatMap(userId ->
          dao.getAllTickets().stream()
          .filter(Ticket::isUnClaimned)
          .sorted(Ticket::compareExpirationDate)
          .findFirst())
        .ifPresent(ticket -> {
          context.result(gson.toJson(ticket));
          ticket.setOwner(optUserId.get());
          dao.update(ticket);
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

package nu.biodela.ticket;

import static io.javalin.ApiBuilder.get;
import static io.javalin.ApiBuilder.path;
import static io.javalin.ApiBuilder.post;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.javalin.Context;
import io.javalin.security.Role;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import nu.biodela.Service;
import nu.biodela.authentication.ApiRole;
import nu.biodela.authentication.session.SessionStore;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

public class TicketService implements Service {
  private final TicketDao dao;
  private final SessionStore sessionStore;
  private final Gson gson;
  private Logger logger;

  @Inject
  TicketService(TicketDao dao, SessionStore sessionStore, Gson gson, ILoggerFactory loggerFactory) {
    this.dao = dao;
    this.sessionStore = sessionStore;
    this.gson = gson;
    this.logger = loggerFactory.getLogger(TicketService.class.getName());
  }

  @Override
  public void setUpRoutes() {
    path("tickets", () -> {
      get(this::getAllTickets, Role.roles(ApiRole.USER));
      post(this::addTicket, Role.roles(ApiRole.USER));
      get("claim", this::claimTicket, Role.roles(ApiRole.USER));
      get("available", this::availableTickets, Role.roles(ApiRole.USER));
    });
  }

  private void availableTickets(Context context) {
    final Optional<Integer> optInt = sessionStore.getActiveUser(context).map(dao::nrOfTickets);
    context.status(500).result("Query failed");
    optInt.ifPresent(integer ->
        context
            .status(200)
            .contentType("application/json")
            .result(gson.toJson(integer)));
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
    Optional<Long> userId = sessionStore.getActiveUser(context);
    try {
      Ticket ticket = gson.fromJson(context.body(), Ticket.class);
      userId.ifPresent(id -> {
        ticket.setProvider(id);
        dao.insert(ticket);
        context.status(200);
      });
    } catch (JsonSyntaxException e) {
      logger.info("Malformed json: " + e.getLocalizedMessage());
      context.status(400);
    }
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

package nu.biodela.ticket;

import java.util.Date;
import javax.inject.Inject;
import nu.biodela.time.TimeProvider;
import nu.biodela.validation.ValidationException;
import nu.biodela.validation.Validator;

/**
 * Validates that a ticket date has not been passed.
 */
public class TicketDateValidator implements Validator<Ticket> {

  private final TimeProvider timeProvider;

  @Inject
  TicketDateValidator(TimeProvider timeProvider) {
    this.timeProvider = timeProvider;
  }

  @Override
  public void validate(Ticket ticket) throws ValidationException {
    Date today = Date.from(timeProvider.now());
    if (today.after(ticket.getExpiryDate())) {
      throw new ValidationException("Expiration date has been!");
    }
  }
}

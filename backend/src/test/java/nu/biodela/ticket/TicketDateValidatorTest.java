package nu.biodela.ticket;

import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;
import nu.biodela.time.TimeProvider;
import nu.biodela.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TicketDateValidatorTest {
  @Mock
  private TimeProvider timeProvider;

  private static final String CODE = "1234";
  private Date today = new Date(1_000_000);
  private TicketDateValidator target;
  private Date creationDate;
  private Date expirationDate;
  private long provider = 1;
  private long owner = 2;

  @Before
  public void setUp() throws Exception {
    target = new TicketDateValidator(timeProvider);
    Calendar cal = Calendar.getInstance();
    cal.setTime(today);
    cal.add(Calendar.DATE, -7);
    creationDate = cal.getTime();
    cal.add(Calendar.DATE, 14);
    expirationDate = cal.getTime();
    when(timeProvider.now()).thenReturn(today.toInstant());
  }

  @Test
  public void validate() throws ValidationException {
    // Given
    Ticket ticket = new Ticket(CODE, expirationDate, creationDate, provider, owner, false);

    // When
    target.validate(ticket);

    // Then: No exception.
  }

  @Test(expected = ValidationException.class)
  public void validateOldTicket() throws ValidationException {
    // Given
    Ticket ticket = new Ticket(CODE, creationDate, creationDate, provider, owner, false);

    // When
    target.validate(ticket);

    // Then: Expect exception.
  }
}
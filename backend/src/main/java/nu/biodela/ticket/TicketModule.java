package nu.biodela.ticket;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import java.util.Set;
import nu.biodela.Service;
import nu.biodela.validation.Validator;

@Module
public abstract class TicketModule {
  @Binds
  @IntoSet
  abstract Service providesTicketService(TicketService impl);

  @Binds
  abstract TicketDao providesTicketDao(SqlTicketDao impl);

  @Provides
  @IntoSet
  static Validator<Ticket> provideTicketDateValidator(TicketDateValidator impl) {
    return impl;
  }
}

package nu.biodela.ticket;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoSet;
import nu.biodela.Service;

@Module
public abstract class TicketModule {
  @Binds
  @IntoSet
  abstract Service providesTicketService(TicketService impl);

  @Binds
  abstract TicketDao providesTicketDao(SqlTicketDao impl);
}

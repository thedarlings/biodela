package nu.biodela;

import dagger.Component;
import javax.inject.Singleton;
import nu.biodela.authentication.AuthModule;
import nu.biodela.db.DbModule;
import nu.biodela.ticket.TicketModule;
import nu.biodela.user.UserModule;

@Singleton
@Component(modules = {
    ServerModule.class,
    UserModule.class,
    AuthModule.class,
    DbModule.class,
    TicketModule.class
})
public interface BioDelarComponent {
  Main getApp();
}

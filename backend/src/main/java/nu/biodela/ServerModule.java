package nu.biodela;

import static io.javalin.ApiBuilder.get;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import io.javalin.Javalin;
import java.util.Set;

@Module
public class ServerModule {
  private final String prefix;
  private final String staticFilePath;
  private final int port;

  public ServerModule(String prefix, String staticFilePath, int port) {
    this.prefix = prefix;
    this.staticFilePath = staticFilePath;
    this.port = port;
  }

  @IntoSet
  @Provides
  Service provideEmptyService() {
    return () -> get(ctx -> ctx.result("Hello!"));
  }

  @Provides
  Main providesMain(Set<Service> services, Javalin javalin) {
    return new Main(services, prefix, javalin);
  }

  @Provides
  Javalin privdesJavalin() {
    return Javalin.create()
        .enableStaticFiles(staticFilePath)
        .enableStandardRequestLogging()
        .port(port);
  }


}

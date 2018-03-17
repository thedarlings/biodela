package nu.biodela;

import static io.javalin.ApiBuilder.get;
import static io.javalin.security.Role.roles;

import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import io.javalin.Javalin;
import javax.inject.Singleton;
import nu.biodela.authentication.ApiRole;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

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
    return () -> get(ctx -> ctx.result("Hello!"), roles(ApiRole.USER));
  }

  @Provides
  Main providesMain(MainFactory factory) {
    return factory.create(prefix);
  }

  @Provides
  Javalin privdesJavalin() {
    return Javalin.create()
        .enableStaticFiles(staticFilePath)
        .enableStandardRequestLogging()
        .port(port);
  }

  @Singleton
  @Provides
  Gson providesGson() {
    return new Gson();
  }

  @Provides
  ILoggerFactory provideLoggerFactory() {
    return LoggerFactory.getILoggerFactory();
  }

}

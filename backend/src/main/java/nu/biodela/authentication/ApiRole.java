package nu.biodela.authentication;

import io.javalin.security.Role;

public enum  ApiRole implements Role {
  ANYONE,
  USER
}

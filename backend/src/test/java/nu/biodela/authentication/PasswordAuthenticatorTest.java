package nu.biodela.authentication;

import org.junit.Before;
import org.junit.Test;

public class PasswordAuthenticatorTest {

  private PasswordAuthenticator target;

  @Before
  public void setUp() throws Exception {
    target = new PasswordAuthenticator();
  }

  @Test
  public void hash() {
    System.out.println(target.hash("lol".toCharArray()));
  }

  @Test
  public void authenticate() {
  }
}
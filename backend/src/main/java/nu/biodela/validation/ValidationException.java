package nu.biodela.validation;

/**
 * Exception is thrown during validation error.
 */
public class ValidationException extends Exception {

  /**
   * Constructs a new Validation exception with an validation error msg.
   * @param msg Message telling the user what was invalid.
   */
  public ValidationException(String msg) {
    super(msg);
  }
}

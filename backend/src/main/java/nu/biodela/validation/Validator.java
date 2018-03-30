package nu.biodela.validation;

/**
 * Validates any object of type T.
 * @param <T> The type of object that is validated.
 */
public interface Validator<T> {
  /**
   * Validates an object. Passes if valid else it throws an exception.
   * @param thing The object to validate.
   * @throws ValidationException If the object is not valid.
   */
  void validate(T thing) throws ValidationException;
}

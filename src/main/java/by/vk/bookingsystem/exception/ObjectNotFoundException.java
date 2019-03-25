package by.vk.bookingsystem.exception;

/**
 * The common error for case when instance was not found.
 *
 * @author Vadzim_Kavalkou
 */
public class ObjectNotFoundException extends RuntimeException {

  /**
   * The constructor. Extends {@link RuntimeException}
   *
   * @param message - the error message
   */
  public ObjectNotFoundException(final String message) {
    super(message);
  }
}

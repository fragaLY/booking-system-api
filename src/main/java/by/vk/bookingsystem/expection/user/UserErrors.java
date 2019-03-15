package by.vk.bookingsystem.expection.user;

public enum UserErrors {
  USER_NOT_FOUND("User not found"),
  INVALID_FIRST_NAME("Invalid first name"),
  INVALID_LAST_NAME("Invalid last name"),
  INVALID_EMAIL("Invalid email"),
  INVALID_PHONE("Invalid phone"),
  INVALID_CURRENCY("Invalid currency"),
  INVALID_COUNTRY("Invalid country");

  private final String message;

  UserErrors(final String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public String getMessage(final String id) {
    return message + "with id : " + id;
  }
}

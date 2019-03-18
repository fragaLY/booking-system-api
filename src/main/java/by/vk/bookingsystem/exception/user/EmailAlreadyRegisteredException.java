package by.vk.bookingsystem.exception.user;

public class EmailAlreadyRegisteredException extends IncorrectPersonalInformationException {

  public EmailAlreadyRegisteredException(String message) {
    super(message);
  }
}

package by.vk.bookingsystem.validator.user.impl;

import java.util.HashSet;
import java.util.Set;

import by.vk.bookingsystem.dto.user.UserDto;
import by.vk.bookingsystem.expection.user.UserErrors;
import by.vk.bookingsystem.expection.user.UserException;
import by.vk.bookingsystem.validator.user.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UserValidatorImpl implements UserValidator {

  @Override
  public Set<ErrorDetail> validate(final UserDto dto) {

    if (dto == null) {
        throw new UserNotFoundException();
    }

    final List<ErrorDetail> errors = new HashSet<>(6);

    if (dto.getFirstName() == null) {
      errors.add(new );
    }

    if (dto.getLastName() == null) {
      errors.add(
          new ErrorDetail(
              HttpStatus.BAD_REQUEST,
              new UserException(dto, UserErrors.INVALID_LAST_NAME.getMessage())));
    }

    if (dto.getEmail() == null) {
      errors.add(
          new ErrorDetail(
              HttpStatus.BAD_REQUEST,
              new UserException(dto, UserErrors.INVALID_EMAIL.getMessage())));
    }

    if (dto.getPhone() == null) {
      errors.add(
          new ErrorDetail(
              HttpStatus.BAD_REQUEST,
              new UserException(dto, UserErrors.INVALID_PHONE.getMessage())));
    }

    if (dto.getCurrencyCode() == null) {
      errors.add(
          new ErrorDetail(
              HttpStatus.BAD_REQUEST,
              new UserException(dto, UserErrors.INVALID_CURRENCY.getMessage())));
    }

    if (dto.getCountry() == null) {
      errors.add(
          new ErrorDetail(
              HttpStatus.BAD_REQUEST,
              new UserException(dto, UserErrors.INVALID_COUNTRY.getMessage())));
    }

    return errors;
  }
}

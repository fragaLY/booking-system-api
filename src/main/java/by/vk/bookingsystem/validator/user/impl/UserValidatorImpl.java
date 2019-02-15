package by.vk.bookingsystem.validator.user.impl;

import java.util.HashSet;
import java.util.Set;

import by.vk.bookingsystem.dto.user.UserDto;
import by.vk.bookingsystem.expection.user.UserErrors;
import by.vk.bookingsystem.expection.user.UserException;
import by.vk.bookingsystem.validator.ErrorDetail;
import by.vk.bookingsystem.validator.user.UserValidator;
import com.google.common.collect.ImmutableSet;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UserValidatorImpl implements UserValidator {

  // todo vk: split it to different validators with custom logic

  @Override
  public Set<ErrorDetail> validate(final UserDto dto) {

    if (dto == null) {
      return ImmutableSet.of(
          new ErrorDetail(
              HttpStatus.NOT_FOUND,
              new UserException(null, UserErrors.USER_NOT_FOUND.getMessage())));
    }

    final Set<ErrorDetail> errorDetails = new HashSet<>(6);

    if (dto.getFirstName() == null) {
      errorDetails.add(
          new ErrorDetail(
              HttpStatus.BAD_REQUEST,
              new UserException(dto, UserErrors.INVALID_FIRST_NAME.getMessage())));
    }

    if (dto.getLastName() == null) {
      errorDetails.add(
          new ErrorDetail(
              HttpStatus.BAD_REQUEST,
              new UserException(dto, UserErrors.INVALID_LAST_NAME.getMessage())));
    }

    if (dto.getEmail() == null) {
      errorDetails.add(
          new ErrorDetail(
              HttpStatus.BAD_REQUEST,
              new UserException(dto, UserErrors.INVALID_EMAIL.getMessage())));
    }

    if (dto.getPhone() == null) {
      errorDetails.add(
          new ErrorDetail(
              HttpStatus.BAD_REQUEST,
              new UserException(dto, UserErrors.INVALID_PHONE.getMessage())));
    }

    if (dto.getCurrencyCode() == null) {
      errorDetails.add(
          new ErrorDetail(
              HttpStatus.BAD_REQUEST,
              new UserException(dto, UserErrors.INVALID_CURRENCY.getMessage())));
    }

    if (dto.getCountry() == null) {
      errorDetails.add(
          new ErrorDetail(
              HttpStatus.BAD_REQUEST,
              new UserException(dto, UserErrors.INVALID_COUNTRY.getMessage())));
    }

    return errorDetails;
  }
}

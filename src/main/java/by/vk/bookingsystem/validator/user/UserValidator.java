package by.vk.bookingsystem.validator.user;

import java.util.Set;

import by.vk.bookingsystem.dto.user.UserDto;
import by.vk.bookingsystem.validator.ErrorDetail;

public interface UserValidator {

  /**
   * Validates the data transfer object
   *
   * @param dto - the data transfer object
   * @return the {@link Set} of {@link UserDto}
   */
  Set<ErrorDetail> validate(UserDto dto);
}

package by.vk.bookingsystem.expection.user;

import by.vk.bookingsystem.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserException extends RuntimeException {

  private final UserDto dto;
  private final String message;
}

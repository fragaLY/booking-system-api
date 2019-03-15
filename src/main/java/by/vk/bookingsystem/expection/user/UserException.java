package by.vk.bookingsystem.expection.user;

import by.vk.bookingsystem.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserException extends RuntimeException {

  private final UserDto dto;
  private final String message;
}

package by.vk.bookingsystem.dto.user;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserSetDto {

  @JsonProperty("users")
  private final Set<UserDto> users;
}

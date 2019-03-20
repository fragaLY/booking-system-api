package by.vk.bookingsystem.dto.user;

import java.util.Set;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserSetDto {

  @JsonProperty("users")
  @Valid
  private final Set<UserDto> users;
}

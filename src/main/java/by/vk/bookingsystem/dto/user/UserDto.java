package by.vk.bookingsystem.dto.user;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@JsonRootName(value = "user")
@Getter
@Setter
@Builder
@EqualsAndHashCode(
    doNotUseGetters = true,
    exclude = {"firstName", "lastName", "role", "email", "phone"})
public class UserDto {

  private String id;

  private final String firstName;
  private final String lastName;
  private final String role;
  private final String email;
  private final String phone;

  @JsonProperty("currency")
  private final String currencyCode;

  private final String country;
  private final String city;
  private final LocalDateTime registered;

  @JsonIgnore private String password;
}

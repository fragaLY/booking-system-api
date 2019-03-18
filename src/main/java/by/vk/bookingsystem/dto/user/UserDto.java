package by.vk.bookingsystem.dto.user;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@JsonRootName(value = "user")
@Setter
@Builder
@EqualsAndHashCode(exclude = {"firstName", "lastName", "role", "email", "phone"})
public class UserDto {

  private static final String LITERALS_ONLY_PATTERN = "[A-Za-z]+";

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

  public String getId() {
    return id;
  }

  @NotNull(message = "The firstname cannot not be null")
  @Pattern(
      regexp = LITERALS_ONLY_PATTERN,
      message = "The firstname cannot contain only the literals")
  @Size(min = 2, max = 100, message = "The size of firstname cannot be between 2 and 100")
  public String getFirstName() {
    return firstName;
  }

  @NotNull(message = "The lastname cannot not be null")
  @Pattern(
      regexp = LITERALS_ONLY_PATTERN,
      message = "The firstname cannot contain only the literals")
  @Size(min = 2, max = 100, message = "The size of lastname cannot be between 2 and 100")
  public String getLastName() {
    return lastName;
  }

  public String getRole() {
    return role;
  }

  @NotNull(message = "The email cannot not be null")
  @Email(message = "The email should be valid")
  public String getEmail() {
    return email;
  }

  @NotBlank(message = "The phone cannot not be blank")
  public String getPhone() {
    return phone;
  }

  @NotBlank(message = "The currency cannot not be null")
  public String getCurrencyCode() {
    return currencyCode;
  }

  @NotBlank(message = "The country cannot not be blank")
  public String getCountry() {
    return country;
  }

  @NotBlank(message = "The city cannot not be blank")
  public String getCity() {
    return city;
  }

  @Past(message = "The registration date should be in the past")
  public LocalDateTime getRegistered() {
    return registered;
  }

  @NotBlank(message = "The password could not be null")
  public String getPassword() {
    return password;
  }
}

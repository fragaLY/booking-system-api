package by.vk.bookingsystem.dto.user;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import by.vk.bookingsystem.validator.user.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonRootName(value = "user")
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"firstName", "lastName", "role", "email", "phone"})
public class UserDto {

  private static final String LITERALS_ONLY_PATTERN = "[A-Za-z]+";

  public static Builder newBuilder() {
    return new UserDto().new Builder();
  }

  private String id;
  private String firstName;
  private String lastName;
  private String role;
  private String email;
  private String phone;

  @JsonProperty("currency")
  private String currencyCode;

  private String country;
  private String city;
  private LocalDateTime registered;
  private String password;

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

  @UserRole
  public String getRole() {
    return role;
  }

  @NotNull(message = "The email cannot not be null")
  @Email(message = "The email should be valid")
  public String getEmail() {
    return email;
  }

  @NotBlank(message = "The currency cannot not be null")
  @Pattern(
      regexp = LITERALS_ONLY_PATTERN,
      message = "The currency code cannot contain only the literals")
  public String getCurrencyCode() {
    return currencyCode;
  }

  @NotBlank(message = "The phone cannot not be blank")
  public String getPhone() {
    return phone;
  }

  @NotBlank(message = "The country cannot not be blank")
  @Pattern(regexp = LITERALS_ONLY_PATTERN, message = "The country cannot contain only the literals")
  public String getCountry() {
    return country;
  }

  @NotBlank(message = "The city cannot not be blank")
  @Pattern(regexp = LITERALS_ONLY_PATTERN, message = "The city cannot contain only the literals")
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

  public class Builder {

    private String id;
    private String firstName;
    private String lastName;
    private String role;
    private String email;
    private String phone;
    private String currencyCode;
    private String country;
    private String city;
    private LocalDateTime registered;
    private String password;

    public Builder setId(final String id) {
      this.id = id;
      return this;
    }

    public Builder setFirstName(final String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder setLastName(final String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder setRole(final String role) {
      this.role = role;
      return this;
    }

    public Builder setEmail(final String email) {
      this.email = email;
      return this;
    }

    public Builder setPhone(final String phone) {
      this.phone = phone;
      return this;
    }

    public Builder setCurrencyCode(final String currencyCode) {
      this.currencyCode = currencyCode;
      return this;
    }

    public Builder setCountry(final String country) {
      this.country = country;
      return this;
    }

    public Builder setCity(final String city) {
      this.city = city;
      return this;
    }

    public Builder setRegistered(final LocalDateTime registered) {
      this.registered = registered;
      return this;
    }

    public Builder setPassword(final String password) {
      this.password = password;
      return this;
    }

    public UserDto build() {
      return UserDto.this;
    }
  }
}

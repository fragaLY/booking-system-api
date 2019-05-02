package by.vk.bookingsystem.dto.user;

import by.vk.bookingsystem.validator.user.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;

/**
 * The user's data transfer object presentation.
 *
 * @author Vadzim_Kavalkou
 */
@JsonRootName(value = "user")
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonPropertyOrder({
  "id",
  "firstName",
  "lastName",
  "email",
  "phone",
  "country",
  "city",
  "registered",
  "role",
  "currency"
})
public class UserDto extends ResourceSupport {

  private static final String LITERALS_ONLY_PATTERN = "[A-Za-z]+";

  @JsonProperty("id")
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

  @JsonIgnore private String password;

  /**
   * Creates new {@link UserDto} and the new one {@link Builder} for it.
   *
   * @return the new instance of {@link Builder}
   */
  public static Builder newBuilder() {
    return new UserDto().new Builder();
  }

  /**
   * Returns the user's id
   *
   * @return {@link String}
   */
  public String getUserId() {
    return id;
  }

  /**
   * The user's firstname. Not able to be null. Literals only. The size between 2 and 100.
   *
   * @return {@link String}
   */
  @NotNull(message = "The firstname cannot not be null")
  @Pattern(
      regexp = LITERALS_ONLY_PATTERN,
      message = "The firstname cannot contain only the literals")
  @Size(min = 2, max = 100, message = "The size of firstname cannot be between 2 and 100")
  public String getFirstName() {
    return firstName;
  }

  /**
   * The user's lastname. Not able to be null. Literals only. The size between 2 and 100.
   *
   * @return {@link String}
   */
  @NotNull(message = "The lastname cannot not be null")
  @Pattern(
      regexp = LITERALS_ONLY_PATTERN,
      message = "The firstname cannot contain only the literals")
  @Size(min = 2, max = 100, message = "The size of lastname cannot be between 2 and 100")
  public String getLastName() {
    return lastName;
  }

  /**
   * Returns the user's role value
   *
   * @return {@link String}
   */
  @UserRole
  public String getRole() {
    return role;
  }

  /**
   * The user's email. Not able to be null. Email validated.
   *
   * @return {@link String}
   */
  @NotNull(message = "The email cannot not be null")
  @Email(message = "The email should be valid")
  public String getEmail() {
    return email;
  }

  /**
   * The user's currency. Not able to be blank. Literals only.
   *
   * @return {@link String}
   */
  @NotBlank(message = "The currency cannot not be null")
  @Pattern(
      regexp = LITERALS_ONLY_PATTERN,
      message = "The currency code cannot contain only the literals")
  public String getCurrencyCode() {
    return currencyCode;
  }

  /**
   * The user's phone. Not able to be blank.
   *
   * @return {@link String}
   */
  @NotBlank(message = "The phone cannot not be blank")
  public String getPhone() {
    return phone;
  }

  /**
   * The user's country. Not able to be blank. Literals only.
   *
   * @return {@link String}
   */
  @NotBlank(message = "The country cannot not be blank")
  @Pattern(regexp = LITERALS_ONLY_PATTERN, message = "The country cannot contain only the literals")
  public String getCountry() {
    return country;
  }

  /**
   * The user's city. Not able to be blank. Literals only.
   *
   * @return {@link String}
   */
  @NotBlank(message = "The city cannot not be blank")
  @Pattern(regexp = LITERALS_ONLY_PATTERN, message = "The city cannot contain only the literals")
  public String getCity() {
    return city;
  }

  /**
   * The user's registration date. Not able to be in future.
   *
   * @return {@link LocalDateTime}
   */
  @Past(message = "The registration date should be in the past")
  public LocalDateTime getRegistered() {
    return registered;
  }

  /**
   * The user's password. Not able to be blank.
   *
   * @return {@link String}
   */
  @NotBlank(message = "The password could not be null")
  public String getPassword() {
    return password;
  }

  /** The Builder of {@link UserDto} */
  public class Builder {

    /**
     * Sets the id in {@link UserDto}
     *
     * @param id - {@link String}
     * @return {@link Builder}
     */
    public Builder setId(final String id) {
      UserDto.this.id = id;
      return this;
    }

    /**
     * Sets the firstname in {@link UserDto}
     *
     * @param firstName - {@link String}
     * @return {@link Builder}
     */
    public Builder setFirstName(final String firstName) {
      UserDto.this.firstName = firstName;
      return this;
    }

    /**
     * Sets the lastName in {@link UserDto}
     *
     * @param lastName - {@link String}
     * @return {@link Builder}
     */
    public Builder setLastName(final String lastName) {
      UserDto.this.lastName = lastName;
      return this;
    }

    /**
     * Sets the role in {@link UserDto}
     *
     * @param role - {@link String}
     * @return {@link Builder}
     */
    public Builder setRole(final String role) {
      UserDto.this.role = role;
      return this;
    }

    /**
     * Sets the email in {@link UserDto}
     *
     * @param email - {@link String}
     * @return {@link Builder}
     */
    public Builder setEmail(final String email) {
      UserDto.this.email = email;
      return this;
    }

    /**
     * Sets the phone in {@link UserDto}
     *
     * @param phone - {@link String}
     * @return {@link Builder}
     */
    public Builder setPhone(final String phone) {
      UserDto.this.phone = phone;
      return this;
    }

    /**
     * Sets the currencyCode in {@link UserDto}
     *
     * @param currencyCode - {@link String}
     * @return {@link Builder}
     */
    public Builder setCurrencyCode(final String currencyCode) {
      UserDto.this.currencyCode = currencyCode;
      return this;
    }

    /**
     * Sets the country in {@link UserDto}
     *
     * @param country - {@link String}
     * @return {@link Builder}
     */
    public Builder setCountry(final String country) {
      UserDto.this.country = country;
      return this;
    }

    /**
     * Sets the city in {@link UserDto}
     *
     * @param city - {@link String}
     * @return {@link Builder}
     */
    public Builder setCity(final String city) {
      UserDto.this.city = city;
      return this;
    }

    /**
     * Sets the registration date in {@link UserDto}
     *
     * @param registered - {@link LocalDateTime}
     * @return {@link Builder}
     */
    public Builder setRegistered(final LocalDateTime registered) {
      UserDto.this.registered = registered;
      return this;
    }

    /**
     * Sets the password in {@link UserDto}
     *
     * @param password - {@link String}
     * @return {@link Builder}
     */
    public Builder setPassword(final String password) {
      UserDto.this.password = password;
      return this;
    }

    /**
     * Returns he data transfer object
     *
     * @return {@link UserDto}
     */
    public UserDto build() {
      return UserDto.this;
    }
  }
}

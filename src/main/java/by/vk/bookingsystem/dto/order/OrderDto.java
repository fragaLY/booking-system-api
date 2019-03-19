package by.vk.bookingsystem.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import by.vk.bookingsystem.dto.home.HomeDto;
import by.vk.bookingsystem.dto.user.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

@JsonRootName("order")
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@EqualsAndHashCode(
    doNotUseGetters = true,
    exclude = {"homes", "user"})
public class OrderDto {

  private final String id;
  private final LocalDateTime from;
  private final LocalDateTime to;
  private final BigDecimal cost;
  private final boolean confirmed;
  private final Set<HomeDto> homes;
  private final UserDto user;
  private byte guests;

  @NotBlank(message = "Price id cannot be blank")
  public String getId() {
    return id;
  }

  @NotNull(message = "The start date of order cannot be null")
  public LocalDateTime getFrom() {
    return from;
  }

  @NotNull(message = "The end date of order cannot be null")
  public LocalDateTime getTo() {
    return to;
  }

  @NotNull(message = "The cost cannot be null")
  @Digits(
      integer = 6,
      fraction = 2,
      message = "The cost should have max 6 digits in integer and 2 digits in fraction")
  public BigDecimal getCost() {
    return cost;
  }

  public boolean isConfirmed() {
    return confirmed;
  }

  @NotNull(message = "The order should has home(s)")
  @Valid
  public Set<HomeDto> getHomes() {
    return homes;
  }

  @NotNull(message = "The order should has owner")
  @Valid
  public UserDto getUser() {
    return user;
  }

  @Range(min = 4, max = 22, message = "The guests amount should be between 4 and 22")
  public byte getGuests() {
    return guests;
  }
}

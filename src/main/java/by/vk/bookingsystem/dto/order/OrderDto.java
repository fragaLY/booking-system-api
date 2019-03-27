package by.vk.bookingsystem.dto.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import by.vk.bookingsystem.dto.home.HomeDto;
import by.vk.bookingsystem.dto.user.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.ResourceSupport;

/**
 * The data transfer object of order.
 *
 * @author Vadzim_Kavalkou
 */
@JsonRootName("order")
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class OrderDto extends ResourceSupport {

  private String id;
  private LocalDate from;
  private LocalDate to;
  private BigDecimal cost;
  private boolean confirmed;

  @Valid private Set<HomeDto> homes;

  @Valid private UserDto owner;
  private int guests;

  /**
   * Creates new {@link OrderDto} and the new one {@link Builder} for it.
   *
   * @return the new instance of {@link Builder}
   */
  public static Builder newBuilder() {
    return new OrderDto().new Builder();
  }

  /**
   * Returns the id of order
   *
   * @return {@link String}
   */
  public String getOrderId() {
    return id;
  }

  /**
   * Returns the date of booking starting. Do not able to be null.
   *
   * @return {@link LocalDate}
   */
  @NotNull(message = "The start date of order cannot be null")
  public LocalDate getFrom() {
    return from;
  }

  /**
   * Returns the date of booking ending. Do not able to be null.
   *
   * @return {@link LocalDate}
   */
  @NotNull(message = "The end date of order cannot be null")
  public LocalDate getTo() {
    return to;
  }

  /**
   * Returns the cost of booking. Do not able to be null.
   *
   * @return {@link BigDecimal}
   */
  @NotNull(message = "The cost cannot be null")
  @Digits(
      integer = 6,
      fraction = 2,
      message = "The cost should have max 6 digits in integer and 2 digits in fraction")
  public BigDecimal getCost() {
    return cost;
  }

  /**
   * Returns the confirmation decision.
   *
   * @return {@link Boolean}
   */
  public boolean isConfirmed() {
    return confirmed;
  }

  /**
   * Returns the homes for booking. Do not able to be null. Should contain valid homes.
   *
   * @return {@link Set} of {@link HomeDto}
   */
  @NotNull(message = "The order should has home(s)")
  @Valid
  public Set<HomeDto> getHomes() {
    return homes;
  }

  /**
   * Returns the owner of booking. Do not able to be null. Should contain valid owner.
   *
   * @return {@link UserDto}
   */
  @NotNull(message = "The order should has owner")
  @Valid
  public UserDto getOwner() {
    return owner;
  }

  /**
   * Returns the amount of guests. Minimal valid values is 4. Max valid value is 22.
   *
   * @return {@link UserDto}
   */
  @Min(value = 4, message = "The guests amount should be equals or greater than 4")
  @Max(value = 22, message = "The guests amount should be equals or less than 22")
  public int getGuests() {
    return guests;
  }

  /** The Builder of {@link OrderDto} */
  public class Builder {

    /**
     * Sets the id in {@link OrderDto}
     *
     * @param id - {@link String}
     * @return {@link Builder}
     */
    public Builder setOrderId(final String id) {
      OrderDto.this.id = id;
      return this;
    }

    /**
     * Sets start date in {@link OrderDto}
     *
     * @param from - {@link LocalDate}
     * @return {@link Builder}
     */
    public Builder setFrom(final LocalDate from) {
      OrderDto.this.from = from;
      return this;
    }

    /**
     * Sets end date in {@link OrderDto}
     *
     * @param to - {@link LocalDate}
     * @return {@link Builder}
     */
    public Builder setTo(final LocalDate to) {
      OrderDto.this.to = to;
      return this;
    }

    /**
     * Sets cost in {@link OrderDto}
     *
     * @param cost - {@link BigDecimal}
     * @return {@link Builder}
     */
    public Builder setCost(final BigDecimal cost) {
      OrderDto.this.cost = cost;
      return this;
    }

    /**
     * Sets confirmation in {@link OrderDto}
     *
     * @param confirmed - {@link Boolean}
     * @return {@link Builder}
     */
    public Builder setConfirmed(final boolean confirmed) {
      OrderDto.this.confirmed = confirmed;
      return this;
    }

    /**
     * Sets homes in {@link OrderDto}
     *
     * @param homes - {@link Set} of {@link HomeDto}
     * @return {@link Builder}
     */
    public Builder setHomes(final Set<HomeDto> homes) {
      OrderDto.this.homes = homes;
      return this;
    }

    /**
     * Sets owner in {@link OrderDto}
     *
     * @param owner - {@link UserDto}
     * @return {@link Builder}
     */
    public Builder setOwner(final UserDto owner) {
      OrderDto.this.owner = owner;
      return this;
    }

    /**
     * Sets the amount of guests in {@link OrderDto}
     *
     * @param guests - {@link Integer}
     * @return {@link Builder}
     */
    public Builder setGuests(final int guests) {
      OrderDto.this.guests = guests;
      return this;
    }

    /**
     * Returns the data transfer object
     *
     * @return {@link OrderDto}
     */
    public OrderDto build() {
      return OrderDto.this;
    }
  }
}

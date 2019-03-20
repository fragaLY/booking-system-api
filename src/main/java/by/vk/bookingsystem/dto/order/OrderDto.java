package by.vk.bookingsystem.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonRootName("order")
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderDto {

  private String id;
  private LocalDateTime from;
  private LocalDateTime to;
  private BigDecimal
      cost; // todo vk: the cost should be built by calculation total day, guests and homes booked
  private boolean confirmed;

  @JsonProperty(value = "home_ids")
  private Set<String> homeIds; // todo vk: validate

  @JsonProperty(value = "owner_id")
  private String ownerId; // todo vk: validate

  private byte guests;

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
  public Set<String> getHomeIds() {
    return homeIds;
  }

  @NotNull(message = "The order should has owner")
  public String getOwnerId() {
    return ownerId;
  }

  @Min(value = 4, message = "The guests amount should be equals or greater than 4")
  @Max(value = 22, message = "The guests amount should be equals or less than 22")
  public byte getGuests() {
    return guests;
  }

  public class Builder {

    public Builder setId(final String id) {
      OrderDto.this.id = id;
      return this;
    }

    public Builder setFrom(final LocalDateTime from) {
      OrderDto.this.from = from;
      return this;
    }

    public Builder setTo(final LocalDateTime to) {
      OrderDto.this.to = to;
      return this;
    }

    public Builder setCost(final BigDecimal cost) {
      OrderDto.this.cost = cost;
      return this;
    }

    public Builder setConfirmed(final boolean confirmed) {
      OrderDto.this.confirmed = confirmed;
      return this;
    }

    public Builder setHomeIds(final Set<String> homeIds) {
      OrderDto.this.homeIds = homeIds;
      return this;
    }

    public Builder setOwnerId(final String ownerId) {
      OrderDto.this.ownerId = ownerId;
      return this;
    }

    public Builder setGuests(final byte guests) {
      OrderDto.this.guests = guests;
      return this;
    }

    public OrderDto build() {
      return OrderDto.this;
    }
  }
}

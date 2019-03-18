package by.vk.bookingsystem.dto.price;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

@JsonRootName("price")
@EqualsAndHashCode
@AllArgsConstructor
public class PriceDto {

  private final String id;
  private final BigDecimal price;
  private final byte guests;

  @NotBlank(message = "Price id cannot be blank")
  public String getId() {
    return id;
  }

  @NotNull(message = "The price cannot be null")
  @Digits(
      integer = 6,
      fraction = 2,
      message = "The price should have max 6 digits in integer and 2 digits in fraction")
  public BigDecimal getPrice() {
    return price;
  }

  @Range(min = 4, max = 22, message = "The guests amount should be between 4 and 22")
  public byte getGuests() {
    return guests;
  }
}

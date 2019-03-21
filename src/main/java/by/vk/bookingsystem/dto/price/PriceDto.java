package by.vk.bookingsystem.dto.price;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@JsonRootName("price")
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PriceDto {

    private String id;
    private BigDecimal price;
    private int guests;

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
  public int getGuests() {
    return guests;
  }
}

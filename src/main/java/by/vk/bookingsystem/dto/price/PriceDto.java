package by.vk.bookingsystem.dto.price;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;

/**
 * The data transfer object of price.
 *
 * @author Vadzim_Kavalkou
 */
@JsonRootName("price")
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "price", "guests"})
public class PriceDto extends ResourceSupport {

  @JsonProperty("id")
  private String id;

  private BigDecimal price;
  private int guests;

  @NotBlank(message = "Price id cannot be blank")
  public String getPriceId() {
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

package by.vk.bookingsystem.dto.price;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@JsonRootName("home")
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class PriceDto {

  private final String id;

  @JsonProperty("price")
  private final BigDecimal pricePerAmount;

  @JsonProperty("guests_amount")
  private final byte guests;
}

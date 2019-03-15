package by.vk.bookingsystem.dto.price;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@JsonRootName("home")
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class PriceDto {

  private final String id;
  private final BigDecimal price;
  private final byte guests;
}

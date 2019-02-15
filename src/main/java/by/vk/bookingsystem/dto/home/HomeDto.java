package by.vk.bookingsystem.dto.home;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@JsonRootName("home")
@Builder
@Getter
@EqualsAndHashCode(
    doNotUseGetters = true,
    exclude = {"name", "price"})
public class HomeDto {

  private final String id;
  private final String name;
  private final BigDecimal price;
}

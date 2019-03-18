package by.vk.bookingsystem.dto.price;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PriceSetDto {

  @JsonProperty("prices")
  private final Set<PriceDto> prices;
}

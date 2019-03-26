package by.vk.bookingsystem.dto.price;

import java.util.Set;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The set of data transfer object of prices.
 *
 * @author Vadzim_Kavalkou
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class PriceSetDto {

  @JsonProperty("prices")
  @Valid
  private final Set<PriceDto> prices;
}

package by.vk.bookingsystem.dto.order;

import java.util.Set;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The set of data transfer object of orders.
 *
 * @author Vadzim_Kavalkou
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class OrderSetDto {

  @JsonProperty("orders")
  @Valid
  private final Set<OrderDto> orders;
}

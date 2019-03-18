package by.vk.bookingsystem.dto.order;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderSetDto {

  @JsonProperty("orders")
  private final Set<OrderDto> orders;

  // todo vk: add links
}

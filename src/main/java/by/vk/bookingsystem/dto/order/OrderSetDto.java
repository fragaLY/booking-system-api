package by.vk.bookingsystem.dto.order;

import java.util.Set;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderSetDto {

  @JsonProperty("orders")
  @Valid
  private final Set<OrderDto> orders;
}

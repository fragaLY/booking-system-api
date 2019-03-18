package by.vk.bookingsystem.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import by.vk.bookingsystem.dto.home.HomeDto;
import by.vk.bookingsystem.dto.user.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@JsonRootName("order")
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Getter
@EqualsAndHashCode(
    doNotUseGetters = true,
    exclude = {"from", "to", "cost", "confirmed", "homes", "user"})
public class OrderDto {

  private final String id;
  private final LocalDateTime from;
  private final LocalDateTime to;
  private final BigDecimal cost;
  private final boolean confirmed;
  private final Set<HomeDto> homes;
  private final UserDto user;
}

package by.vk.bookingsystem.validator.order;

import java.util.Set;

import by.vk.bookingsystem.dto.home.HomeDto;
import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.dto.user.UserDto;

/**
 * The validator of {@link OrderDto}
 *
 * @author Vadzim_Kavalkou
 */
public interface OrderValidator {

  /**
   * Validates the owner of order.
   *
   * @param owner - {@link UserDto}
   */
  void validateOwner(UserDto owner);

  /**
   * Validates the homes that are in order
   *
   * @param homes - the set of {@link HomeDto}
   */
  void validateHomes(Set<HomeDto> homes);

  /**
   * Validates the order dates.
   *
   * @param order - {@link OrderDto}
   */
  void validateOrderDates(OrderDto order);
}

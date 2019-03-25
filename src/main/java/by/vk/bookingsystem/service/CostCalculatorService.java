package by.vk.bookingsystem.service;

import java.math.BigDecimal;

import by.vk.bookingsystem.dto.order.OrderDto;

/**
 * The cost calculator service.
 *
 * @author Vadzim_Kavalkou
 */
public interface CostCalculatorService {

  /**
   * Calculates the cost of order by input values (dates and guests amount).
   *
   * @param dto - {@link OrderDto}
   * @return {@link BigDecimal}
   */
  BigDecimal calculateCost(OrderDto dto);
}

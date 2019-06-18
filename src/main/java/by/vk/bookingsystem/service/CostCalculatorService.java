package by.vk.bookingsystem.service;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The cost calculator service.
 *
 * @author Vadzim_Kavalkou
 */
public interface CostCalculatorService {

  /**
   * Calculates the cost of order by input values (dates and guests amount).
   *
   * @param from - {@link LocalDate} the start date
   * @param to - {@link LocalDate} the end date
   * @param guests - amount of guests
   * @return {@link BigDecimal}
   */
  BigDecimal calculateCost(LocalDate from, LocalDate to, int guests);
}

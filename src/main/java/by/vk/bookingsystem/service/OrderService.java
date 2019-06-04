package by.vk.bookingsystem.service;

import java.time.LocalDate;

import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.dto.order.OrderSetDto;
import org.springframework.data.domain.Pageable;

/**
 * The service for {@link OrderDto}
 *
 * @author Vadzim_Kavalkou
 */
public interface OrderService {

  /**
   * Finds all orders between selected dates in the system and returns them
   *
   * <p>By default date frame the last month
   *
   * @param from {@link LocalDate}
   * @param to {@link LocalDate}
   * @param pageable {@link Pageable}
   * @return {@link OrderSetDto}
   */
  OrderSetDto findAllOrdersBetweenDates(Pageable pageable, LocalDate from, LocalDate to);

  /**
   * Finds the order by its id.
   *
   * @param id - the id of order
   * @return {@link OrderDto}
   */
  OrderDto findOrderById(String id);

  /**
   * Creates the order and returns its id
   *
   * @param dto - {@link OrderDto}
   * @return {@link String}
   */
  String createOrder(OrderDto dto);

  /**
   * Enriches the order with new information from data transfer object and updates it.
   *
   * @param dto - {@link OrderDto}
   * @param id - the id of order.
   */
  void updateOrder(OrderDto dto, String id);

  /**
   * Deletes order by its id.
   *
   * @param id - the id of order
   */
  void deleteOrderById(String id);
}

package by.vk.bookingsystem.dao;

import java.time.LocalDate;
import java.util.List;

import by.vk.bookingsystem.domain.Order;

/**
 * The data access object layer for {@link Order}
 *
 * @author Vadzim_Kavalkou
 */
public interface OrderDao {

  /**
   * Finds all orders between dates and returns it
   *
   * @param from {@link LocalDate}
   * @param to {@link LocalDate}
   * @return {@link List} of {@link Order}
   */
  List<Order> findAllOrdersBetweenDates(LocalDate from, LocalDate to);

  /**
   * Finds the order by its id and returns it
   *
   * @param id - the id of {@link Order}
   * @return {@link Order}
   */
  Order findOrderById(String id);

  /**
   * Save or update order
   *
   * @param order {@link Order}
   * @return {@link Order}
   */
  <S extends Order> S save(S order);

  /**
   * Deletes the order by id
   *
   * @param id - the id of {@link Order}
   */
  void deleteById(String id);

  /**
   * Finds checks if order intersects with existing orders
   *
   * @param from - {@link LocalDate}
   * @param to - {@link LocalDate}
   * @return true if intersects
   */
  boolean intersectedWithExistedOrders(LocalDate from, LocalDate to);

  /**
   * Checks if order exists.
   *
   * @param id - the id of {@link Order}
   * @return true if order exists, false if
   */
  boolean existsById(String id);

  /**
   * Retrieves all orders that were registered between selected dates
   *
   * @param from {@link LocalDate}
   * @param to {@link LocalDate}
   * @return {@link List} of {@link Order}
   */
  List<Order> findOrdersRegisteredBetweenDates(LocalDate from, LocalDate to);
}

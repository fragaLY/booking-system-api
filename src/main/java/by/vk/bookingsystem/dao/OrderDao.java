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
   * Finds all orders that are in the system and returns it
   *
   * @return the list of {@link Order}
   */
  List<Order> findAll();

  /**
   * Finds the order by its id and returns it
   *
   * @param id - the id of {@link Order}
   * @return {@link Order}
   */
  Order findOrderById(String id);

  /**
   * Saves the order.
   *
   * @param order - {@link Order}
   * @return {@link Order}
   */
  Order save(Order order);

  /**
   * Deletes the order by id
   *
   * @param id - the id of {@link Order}
   */
  void deleteById(String id);

  /**
   * Finds all the orders that intersects with new one.
   *
   * @param from - {@link LocalDate} of the starting booking time
   * @param to - {@link LocalDate} of the ending booking time
   * @return the list of {@link Order}
   */
  // todo get all intersection orders
  List<Order> findOrdersByFromBetweenOrToBetween(LocalDate from, LocalDate to);

  /**
   * Checks if order exists.
   *
   * @param id - the id of {@link Order}
   * @return true if order exists, false if
   */
  boolean existsById(String id);
}

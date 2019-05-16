package by.vk.bookingsystem.dao;

import java.time.LocalDate;
import java.util.List;

import by.vk.bookingsystem.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The data access object layer for {@link Order}
 *
 * @author Vadzim_Kavalkou
 */
public interface OrderDao {

  /**
   * Finds all orders that are in the system and returns it
   *
   * @param pageable {@link Pageable}
   * @return {@link Page} of {@link Order}
   */
  Page<Order> findAll(Pageable pageable);

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
   * @param order the {@link Order}
   * @param <S> the instance of {@link Order}
   * @return {@link Order}
   */
  <S extends Order> S insert(S order);

  /**
   * Deletes the order by id
   *
   * @param id - the id of {@link Order}
   */
  void deleteById(String id);

  /**
   * Finds all the orders that intersects with new one.
   *
   * @param from - {@link LocalDate}
   * @param to - {@link LocalDate}
   * @return the list of {@link Order}
   */
  boolean existsByFromAndTo(LocalDate from, LocalDate to);

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

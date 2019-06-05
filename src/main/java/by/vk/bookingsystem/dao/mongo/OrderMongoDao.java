package by.vk.bookingsystem.dao.mongo;

import java.time.LocalDate;
import java.util.List;

import by.vk.bookingsystem.dao.OrderDao;
import by.vk.bookingsystem.domain.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * The data access object layer for {@link Order}
 *
 * @author Vadzim_Kavalkou
 */
public interface OrderMongoDao extends MongoRepository<Order, ObjectId>, OrderDao {

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
  @Override
  <S extends Order> S save(S order);

  /**
   * Deletes the order by id
   *
   * @param id - the id of {@link Order}
   */
  void deleteById(String id);

  /**
   * Checks if order exists.
   *
   * @param id - the id of {@link Order}
   * @return true if order exists, false if
   */
  boolean existsById(String id);

  /**
   * Finds orders between dates and returns them
   *
   * @param from {@link LocalDate}
   * @param to {@link LocalDate}
   * @return {@link List} of {@link Order}
   */
  @Query(value = "{ 'from' : { $gte: ?0 }, 'to' : { $lte: ?1 }}")
  List<Order> findAllOrdersBetweenDates(LocalDate from, LocalDate to);

  /**
   * Finds checks if order intersects with existing orders
   *
   * @param from - {@link LocalDate}
   * @param to - {@link LocalDate}
   * @return true if intersects
   */
  @Query(value = "{ 'from' : {$lt: ?0}, 'to' : {$gt: ?1} }", exists = true)
  boolean intersectedWithExistedOrders(LocalDate to, LocalDate from);

  /**
   * Retrieves all orders that were registered between selected dates
   *
   * @param from {@link LocalDate}
   * @param to {@link LocalDate}
   * @return {@link List} of {@link Order}
   */
  @Query(value = "{ 'from' : {$gte: ?0, $lte:?1 }}", sort = "{ from : -1 }")
  List<Order> findOrdersRegisteredBetweenDates(LocalDate from, LocalDate to);
}

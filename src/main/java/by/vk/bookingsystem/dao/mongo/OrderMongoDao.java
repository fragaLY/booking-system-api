package by.vk.bookingsystem.dao.mongo;

import java.time.LocalDate;
import java.util.List;

import by.vk.bookingsystem.domain.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * The data access object layer for {@link Order}
 *
 * @author Vadzim_Kavalkou
 */
public interface OrderMongoDao extends ReactiveMongoRepository<Order, ObjectId> {

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

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
   * @param from - {@link LocalDate}
   * @param to - {@link LocalDate}
   * @return the list of {@link Order}
   */
  @Query(
      "{ $or: [{'from' : {$range:[?0, ?1]}}, {'to':{$range:[?0, ?1]}}, {$and:[{'from': {$lte: ?0}},{'to': {$gte: ?1}}]}] }")
  boolean existsByFromAndTo(LocalDate from, LocalDate to);

  /**
   * Checks if order exists.
   *
   * @param id - the id of {@link Order}
   * @return true if order exists, false if
   */
  boolean existsById(String id);
}

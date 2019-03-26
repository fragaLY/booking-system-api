package by.vk.bookingsystem.dao.mongo;

import java.util.List;

import by.vk.bookingsystem.dao.PriceDao;
import by.vk.bookingsystem.domain.Price;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * The data access object layer for {@link Price}
 *
 * @author Vadzim_Kavalkou
 */
public interface PriceMongoDao extends MongoRepository<Price, ObjectId>, PriceDao {

  /**
   * Finds all prices that are in the system and returns it
   *
   * @return the list of {@link Price}
   */
  List<Price> findAll();

  /**
   * Finds the price by its id and returns it
   *
   * @param id - the id of {@link Price}
   * @return {@link Price}
   */
  Price findPriceById(String id);

  /**
   * Finds the price that is present for current number of guests
   *
   * @param guests - the amount of guests
   * @return {@link Price}
   */
  Price findPriceByGuests(int guests);

  /**
   * Checks if price exists.
   *
   * @param id - the id of {@link Price}
   * @return true if price exists, false if
   */
  boolean existsById(String id);
}

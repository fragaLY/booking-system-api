package by.vk.bookingsystem.dao;

import java.util.List;

import by.vk.bookingsystem.domain.Home;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * The data access object layer for {@link Home}
 *
 * @author Vadzim_Kavalkou
 */
public interface HomeMongoDao extends MongoRepository<Home, ObjectId> {

  /**
   * Finds all homes that are in the system and returns it
   *
   * @return the list of {@link Home}
   */
  List<Home> findAll();

  /**
   * Finds the home by its id and returns it
   *
   * @param id - the id of {@link Home}
   * @return {@link Home}
   */
  Home findHomeById(String id);
}

package by.vk.bookingsystem.dao;

import java.util.List;
import java.util.Set;

import by.vk.bookingsystem.domain.Home;
import org.bson.types.ObjectId;

/**
 * The data access object layer for {@link Home}
 *
 * @author Vadzim_Kavalkou
 */
public interface HomeDao {

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

  /**
   * Finds all homes by ids
   *
   * @param ids - the set of {@link ObjectId}
   * @return the list of {@link Home}
   */
  List<Home> findAllById(Set<ObjectId> ids);

  /**
   * Checks if home exists.
   *
   * @param id - the id of {@link Home}
   * @return true if home exists, false if
   */
  boolean existsById(String id);
}

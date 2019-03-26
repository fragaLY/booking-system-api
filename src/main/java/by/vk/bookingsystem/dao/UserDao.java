package by.vk.bookingsystem.dao;

import java.util.List;

import by.vk.bookingsystem.domain.User;
import org.bson.types.ObjectId;

/**
 * The data access object layer for {@link User}
 *
 * @author Vadzim_Kavalkou
 */
public interface UserDao {

  /**
   * Finds all users that are in the system
   *
   * @return the list of {@link User}
   */
  List<User> findAll();

  /**
   * Finds the user by id
   *
   * @param id - the id of {@link User}
   * @return {@link User}
   */
  User findUserById(String id);

  /**
   * Checks if email is already in use
   *
   * @param email - the email of {@link User}
   * @return true if email in use, if not - false
   */
  boolean existsByEmail(String email);

  /**
   * Checks if phone is already in use
   *
   * @param phone - the phone of {@link User}
   * @return true if phone in use, if not - false
   */
  boolean existsByPhone(String phone);

  /**
   * Saves user
   *
   * @param user - {@link User}
   * @return {@link User}
   */
  User save(User user);

  /**
   * Deletes the user by id
   *
   * @param id - the id of {@link User}
   */
  void deleteById(ObjectId id);

  /**
   * Checks if user exists.
   *
   * @param id - the id of {@link User}
   * @return true if user exists, false if
   */
  boolean existsById(String id);
}

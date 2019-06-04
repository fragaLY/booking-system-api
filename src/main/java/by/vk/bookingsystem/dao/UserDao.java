package by.vk.bookingsystem.dao;

import java.time.LocalDate;
import java.util.List;

import by.vk.bookingsystem.domain.Order;
import by.vk.bookingsystem.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The data access object layer for {@link User}
 *
 * @author Vadzim_Kavalkou
 */
public interface UserDao {

  /**
   * Finds all users between dates and returns it
   *
   * @param pageable {@link Pageable}
   * @param from {@link LocalDate}
   * @param to {@link LocalDate}
   * @return {@link Page} of {@link Order}
   */
  Page<User> findAllOrdersBetweenDates(Pageable pageable, LocalDate from, LocalDate to);

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
   * Save or update user
   *
   * @param user {@link User}
   * @return {@link User}
   */
  <S extends User> S save(S user);

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

  /**
   * Retrieves all users that were registered between selected dates
   *
   * @param from {@link LocalDate}
   * @param to {@link LocalDate}
   * @return {@link List} of {@link User}
   */
  List<User> findUsersRegisteredBetweenDates(LocalDate from, LocalDate to);
}

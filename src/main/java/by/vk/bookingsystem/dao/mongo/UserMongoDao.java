package by.vk.bookingsystem.dao.mongo;

import java.time.LocalDate;
import java.util.List;

import by.vk.bookingsystem.dao.UserDao;
import by.vk.bookingsystem.domain.Order;
import by.vk.bookingsystem.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * The data access object layer for {@link User}
 *
 * @author Vadzim_Kavalkou
 */
public interface UserMongoDao extends MongoRepository<User, ObjectId>, UserDao {

  /**
   * Finds all users that are in the system
   *
   * @param pageable {@link Pageable}
   * @return {@link Page} of {@link User}
   */
  Page<User> findAll(Pageable pageable);

  /**
   * Finds users between dates and returns them
   *
   * @param pageable {@link Pageable}
   * @param from {@link LocalDate}
   * @param to {@link LocalDate}
   * @return {@link List} of {@link Order}
   */
  @Query(value = "{ 'registered' : { $gte: ?0 }, 'to' : { $lte: ?1 }}")
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
  @Override
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
   * @return {@link List}
   */
  @Query(value = "{ 'registered' : {$gte: ?0, $lte:?1 }}", sort = "{ registered : -1 }")
  List<User> findUsersRegisteredBetweenDates(LocalDate from, LocalDate to);
}

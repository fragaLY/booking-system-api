package by.vk.bookingsystem.dao.mongo;

import by.vk.bookingsystem.dao.UserDao;
import by.vk.bookingsystem.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

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
  <S extends User> S insert(S user);

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

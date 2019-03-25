package by.vk.bookingsystem.dao.mongo;

import java.util.List;

import by.vk.bookingsystem.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * The data access object layer for {@link User}
 *
 * @author Vadzim_Kavalkou
 */
public interface UserMongoDao
    extends MongoRepository<User, ObjectId>, by.vk.bookingsystem.dao.UserDao {

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
   * Finds the user by email
   *
   * @param email - the email of {@link User}
   * @return {@link User}
   */
  User findUserByEmail(String email);

  /**
   * Finds the user by phone
   *
   * @param phone - the phone of {@link User}
   * @return {@link User}
   */
  User findUserByPhone(String phone);

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
}

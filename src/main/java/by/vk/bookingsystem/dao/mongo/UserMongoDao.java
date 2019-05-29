package by.vk.bookingsystem.dao.mongo;

import java.time.LocalDate;
import java.util.List;

import by.vk.bookingsystem.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
 * The data access object layer for {@link User}
 *
 * @author Vadzim_Kavalkou
 */
public interface UserMongoDao extends ReactiveMongoRepository<User, ObjectId> {

  /**
   * Retrieves all users that were registered between selected dates
   *
   * @param from {@link LocalDate}
   * @param to {@link LocalDate}
   * @return {@link List}
   */
  @Query(value = "{ 'registered' : {$gte: ?0, $lte:?1 }}", sort = "{ registered : -1 }")
  Flux<User> findUsersRegisteredBetweenDates(LocalDate from, LocalDate to);
}

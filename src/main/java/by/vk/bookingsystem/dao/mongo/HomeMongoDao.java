package by.vk.bookingsystem.dao.mongo;

import by.vk.bookingsystem.domain.Home;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * The data access object layer for {@link Home}
 *
 * @author Vadzim_Kavalkou
 */
public interface HomeMongoDao extends ReactiveMongoRepository<Home, ObjectId> {}

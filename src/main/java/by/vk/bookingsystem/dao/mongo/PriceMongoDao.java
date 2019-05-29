package by.vk.bookingsystem.dao.mongo;

import by.vk.bookingsystem.domain.Price;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * The data access object layer for {@link Price}
 *
 * @author Vadzim_Kavalkou
 */
public interface PriceMongoDao extends ReactiveMongoRepository<Price, ObjectId> {}

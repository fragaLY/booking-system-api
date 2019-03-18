package by.vk.bookingsystem.dao;

import java.util.List;

import by.vk.bookingsystem.domain.Price;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PriceDao extends MongoRepository<Price, ObjectId> {

  List<Price> findAll();

  Price findPriceById(String id);
}

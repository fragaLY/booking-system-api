package by.vk.bookingsystem.dao;

import java.time.LocalDateTime;
import java.util.List;

import by.vk.bookingsystem.domain.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderMongoDao extends MongoRepository<Order, ObjectId> {

  List<Order> findAll();

  Order findOrderById(String id);

  Order save(Order order);

  void deleteById(String id);

  List<Order> findOrdersByFromBetweenAndToBetween(LocalDateTime from, LocalDateTime to);
}

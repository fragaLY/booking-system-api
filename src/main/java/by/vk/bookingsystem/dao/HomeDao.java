package by.vk.bookingsystem.dao;

import java.util.List;

import by.vk.bookingsystem.domain.Home;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HomeDao extends MongoRepository<Home, ObjectId> {

  List<Home> findAll();
}

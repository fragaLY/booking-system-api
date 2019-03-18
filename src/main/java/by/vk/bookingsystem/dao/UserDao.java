package by.vk.bookingsystem.dao;

import java.util.List;

import by.vk.bookingsystem.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDao extends MongoRepository<User, ObjectId> {

  List<User> findAll();

  User findUserById(String id);

  User findUserByEmail(String email);

  User findUserByPhone(String phone);

  User save(User user);

  void deleteById(ObjectId id);
}

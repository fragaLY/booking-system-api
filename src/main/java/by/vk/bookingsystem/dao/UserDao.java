package by.vk.bookingsystem.dao;

import java.time.LocalDateTime;
import java.util.Set;

import by.vk.bookingsystem.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDao extends MongoRepository<User, String> {

  User getUsersByEmail(String email);

  Set<User> findAllByRegisteredBetween(LocalDateTime from, LocalDateTime to);
}

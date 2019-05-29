package by.vk.bookingsystem.service;

import by.vk.bookingsystem.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The service for users
 *
 * @author Vadzim_Kavalkou
 */
public interface UserService {

  Flux<User> findAllUsers();

  Mono<User> findUserById(String id);

  Mono<User> createUser(User user);

  Flux<User> updateUser(User user, String id);

  Mono<Void> deleteUserById(String id);
}

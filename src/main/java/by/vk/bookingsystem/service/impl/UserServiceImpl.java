package by.vk.bookingsystem.service.impl;

import by.vk.bookingsystem.dao.mongo.UserMongoDao;
import by.vk.bookingsystem.domain.User;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * The service implementation for users
 *
 * @author Vadzim_Kavalkou
 */
@Service
@PropertySources(@PropertySource("classpath:i18n/validation_errors.properties"))
public class UserServiceImpl implements UserService {

  private static final String USER_NOT_FOUND = "user.not.found";

  private final UserMongoDao userDao;
  private final Environment environment;

  @Autowired
  public UserServiceImpl(final UserMongoDao userDao, final Environment environment) {
    this.userDao = userDao;
    this.environment = environment;
  }

  @Override
  public Flux<User> findAllUsers() {
    return userDao.findAll();
  }

  @Override
  public Mono<User> findUserById(final String id) {
    return userDao
        .findById(new ObjectId(id))
        .switchIfEmpty(
            Mono.error(new ObjectNotFoundException(environment.getProperty(USER_NOT_FOUND))));
  }

  @Override
  public Mono<User> createUser(final User user) {
    return userDao.insert(user);
  }

  @Override
  public Flux<User> updateUser(final User user, final String id) {
    return userDao.insert(
        userDao
            .findById(new ObjectId(id))
            .switchIfEmpty(
                Mono.error(new ObjectNotFoundException(environment.getProperty(USER_NOT_FOUND))))
            .flatMap(
                data ->
                    Mono.just(data)
                        .map(
                            updatedUser -> {
                              updatedUser.setEmail(user.getEmail());
                              updatedUser.setPhone(user.getPhone());
                              return updatedUser;
                            })
                        .subscribeOn(Schedulers.parallel())));
  }

  @Override
  public Mono<Void> deleteUserById(final String id) {
    return userDao
        .deleteById(new ObjectId(id))
        .switchIfEmpty(
            Mono.error(new ObjectNotFoundException(environment.getProperty(USER_NOT_FOUND))));
  }
}

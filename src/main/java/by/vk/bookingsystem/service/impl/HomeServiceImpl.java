package by.vk.bookingsystem.service.impl;

import by.vk.bookingsystem.dao.mongo.HomeMongoDao;
import by.vk.bookingsystem.domain.Home;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.HomeService;
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
 * The service implementation for homes.
 *
 * @author Vadzim_Kavalkou
 */
@Service
@PropertySources(@PropertySource("classpath:i18n/validation_errors.properties"))
public class HomeServiceImpl implements HomeService {

  private static final String HOME_NOT_FOUND = "home.not.found";

  private final HomeMongoDao homeDao;
  private final Environment environment;

  @Autowired
  public HomeServiceImpl(final HomeMongoDao homeDao, final Environment environment) {
    this.homeDao = homeDao;
    this.environment = environment;
  }

  @Override
  public Flux<Home> findAllHomes() {
    return homeDao.findAll();
  }

  @Override
  public Mono<Home> findHomeById(final String id) {
    return homeDao
        .findById(new ObjectId(id))
        .switchIfEmpty(
            Mono.error(new ObjectNotFoundException(environment.getProperty(HOME_NOT_FOUND))))
        .subscribeOn(Schedulers.elastic());
  }
}

package by.vk.bookingsystem.service.impl;

import by.vk.bookingsystem.dao.mongo.PriceMongoDao;
import by.vk.bookingsystem.domain.Price;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.PriceService;
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
 * The service implementation for prices
 *
 * @author Vadzim_Kavalkou
 */
@Service
@PropertySources(@PropertySource("classpath:i18n/validation_errors.properties"))
public class PriceServiceImpl implements PriceService {

  private static final String PRICE_NOT_FOUND = "price.not.found";

  private final PriceMongoDao priceDao;
  private final Environment environment;

  @Autowired
  public PriceServiceImpl(final PriceMongoDao priceDao, final Environment environment) {
    this.priceDao = priceDao;
    this.environment = environment;
  }

  @Override
  public Flux<Price> findAllPrices() {
    return priceDao.findAll();
  }

  @Override
  public Mono<Price> findPriceById(final String id) {
    return priceDao
        .findById(new ObjectId(id))
        .switchIfEmpty(
            Mono.error(new ObjectNotFoundException(environment.getProperty(PRICE_NOT_FOUND))))
        .subscribeOn(Schedulers.elastic());
  }
}

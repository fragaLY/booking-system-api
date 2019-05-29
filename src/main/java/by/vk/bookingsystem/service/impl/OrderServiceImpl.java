package by.vk.bookingsystem.service.impl;

import by.vk.bookingsystem.dao.mongo.OrderMongoDao;
import by.vk.bookingsystem.dao.mongo.UserMongoDao;
import by.vk.bookingsystem.domain.Order;
import by.vk.bookingsystem.service.OrderService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The service implementation for orders
 *
 * @author Vadzim_Kavalkou
 */
@Service
@PropertySources(@PropertySource("classpath:i18n/validation_errors.properties"))
public class OrderServiceImpl implements OrderService {

  private static final String OWNER_NOT_FOUND = "owner.not.found";
  private static final String ORDER_NOT_FOUND = "order.not.found";

  private final UserMongoDao userDao;
  private final OrderMongoDao orderDao;
  private final Environment environment;

  @Autowired
  public OrderServiceImpl(
      final UserMongoDao userDao, final OrderMongoDao orderDao, final Environment environment) {
    this.userDao = userDao;
    this.orderDao = orderDao;
    this.environment = environment;
  }

  @Override
  public Flux<Order> findAllOrders() {
    return orderDao.findAll();
  }

  @Override
  public Mono<Order> findOrderById(final String id) {
    return orderDao.findById(new ObjectId(id));
  }

  @Override
  public Mono<Void> createOrder(final Order order) {
    // todo vk: validate order and set it cost
    return orderDao.insert(order).then();
  }

  @Override
  public Mono<Void> updateOrder(final Order order, final String id) {
    return orderDao.insert(order).then();
  }

  @Override
  public Mono<Void> deleteOrderById(final String id) {
    return orderDao.deleteById(new ObjectId(id)).then();
  }
}

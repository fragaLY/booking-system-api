package by.vk.bookingsystem.validator.order.impl;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import by.vk.bookingsystem.dao.HomeDao;
import by.vk.bookingsystem.dao.OrderDao;
import by.vk.bookingsystem.dao.UserDao;
import by.vk.bookingsystem.domain.Home;
import by.vk.bookingsystem.dto.home.HomeDto;
import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.dto.user.UserDto;
import by.vk.bookingsystem.validator.order.OrderValidator;
import com.google.common.collect.Lists;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * The validator implementation for {@link OrderDto}
 *
 * @author Vadzim_Kavalkou
 */
@Component
@PropertySources(@PropertySource("classpath:i18n/validation_errors.properties"))
public class OrderValidatorImpl implements OrderValidator {

  private static final String OWNER_NOT_FOUND = "owner.not.found";
  private static final String HOMES_NOT_FOUND = "homes.not.found";
  private static final String INTERSECTING_DATES = "order.dates.intersection";

  private final OrderDao orderDao;
  private final UserDao userDao;
  private final HomeDao homeDao;
  private final Environment environment;

  /**
   * @param userDao - {@link UserDao}
   * @param homeDao - {@link HomeDao}
   * @param orderDao - {@link OrderDao}
   * @param environment - {@link Environment}
   */
  @Autowired
  public OrderValidatorImpl(
      final UserDao userDao,
      final HomeDao homeDao,
      final OrderDao orderDao,
      final Environment environment) {
    this.orderDao = orderDao;
    this.userDao = userDao;
    this.homeDao = homeDao;
    this.environment = environment;
  }

  /**
   * Validates the owner of order. In case of missing owner throws {@link IllegalArgumentException}
   *
   * @param owner - {@link UserDto}
   */
  public void validateOwner(final UserDto owner) {
    if (userDao.findUserById(owner.getId()) == null) {
      throw new IllegalArgumentException(environment.getProperty(OWNER_NOT_FOUND));
    }
  }

  /**
   * Validates the homes that are in order. In case of missing home(s) throws {@link
   * IllegalArgumentException}
   *
   * @param homes - the set of {@link HomeDto}
   */
  public void validateHomes(final Set<HomeDto> homes) {
    final List<Home> validHomes =
        Lists.newArrayList(
            homeDao.findAllById(
                homes.stream()
                    .filter(Objects::nonNull)
                    .map(HomeDto::getId)
                    .map(ObjectId::new)
                    .collect(Collectors.toSet())));

    if (validHomes.isEmpty()) {
      throw new IllegalArgumentException(environment.getProperty(HOMES_NOT_FOUND));
    }
  }

  @Override

  // todo vk: rework solution
  /**
   * Validates the order dates. In case of intersection dates with already existing orders throws
   * {@link IllegalArgumentException}
   *
   * @param order - {@link OrderDto}
   */
  public void validateOrderDates(final OrderDto order) {
    //    final LocalDateTime from = order.getFrom().atTime(12, 0, 0, 1);
    //    final LocalDateTime to = order.getTo().atTime(11, 59, 59, 999_999_999);
    //    final List<Order> intersecting = orderDao.findOrdersByFromBetweenAndToBetween(from, to);
    //
    //    if (intersecting != null && !intersecting.isEmpty()) {
    //      throw new IllegalArgumentException(environment.getProperty(INTERSECTING_DATES));
    //    }
  }
}

package by.vk.bookingsystem.validator.order.impl;

import by.vk.bookingsystem.dao.HomeDao;
import by.vk.bookingsystem.dao.OrderDao;
import by.vk.bookingsystem.dao.UserDao;
import by.vk.bookingsystem.domain.Home;
import by.vk.bookingsystem.dto.home.HomeDto;
import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.dto.user.UserDto;
import by.vk.bookingsystem.validator.order.OrderValidator;
import javax.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The validator implementation for {@link OrderDto}
 *
 * @author Vadzim_Kavalkou
 */
@Component
@PropertySources(@PropertySource("classpath:i18n/validation_errors.properties"))
public class OrderValidatorImpl implements OrderValidator {

  private static final Logger LOGGER = LoggerFactory.getLogger(OrderValidatorImpl.class);

  private static final String OWNER_NOT_FOUND = "owner.not.found";
  private static final String HOMES_NOT_FOUND = "homes.not.found";
  private static final String INVALID_ORDER_DATES = "order.dates.invalid";
  private static final String INTERSECTING_DATES = "order.dates.intersection";

  private static final String OWNER_NOT_FOUND_LOG = "The user {} was not found";
  private static final String HOMES_NOT_FOUND_LOG = "The homes {} were not found";
  private static final String INVALID_ORDER_DATES_LOG =
      "The from date should be before to date for order{}.";
  private static final String INTERSECTING_DATES_LOG =
      "The dates for order {} intersect with already existing";

  private final OrderDao orderDao;
  private final UserDao userDao;
  private final HomeDao homeDao;
  private final Environment environment;

  /**
   * The constructor with parameters
   *
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
   * @param owner - {@link UserDto}. Not null value.
   */
  public void validateOwner(final UserDto owner) {
    if (userDao.findUserById(owner.getUserId()) == null) {
      LOGGER.error(OWNER_NOT_FOUND_LOG, owner);
      throw new IllegalArgumentException(environment.getProperty(OWNER_NOT_FOUND));
    }
  }

  /**
   * Validates the homes that are in order. In case of missing home(s) throws {@link
   * IllegalArgumentException}
   *
   * @param homes - the set of {@link HomeDto}. Not null value.
   */
  public void validateHomes(final Set<HomeDto> homes) {
    final List<Home> validHomes =
        homeDao.findAllById(
            homes.stream()
                .filter(Objects::nonNull)
                .map(HomeDto::getHomeId)
                .map(ObjectId::new)
                .collect(Collectors.toSet()));

    if (validHomes == null || validHomes.isEmpty()) {
      LOGGER.error(HOMES_NOT_FOUND_LOG, homes);
      throw new IllegalArgumentException(environment.getProperty(HOMES_NOT_FOUND));
    }
  }

  /**
   * Validates the order dates. In case of intersection dates with already existing orders throws
   * {@link IllegalArgumentException}
   *
   * @param order - {@link OrderDto}. Not null value.
   */
  @Override
  public void validateOrderDates(@NotNull final OrderDto order) {

    final LocalDate from = order.getFrom();
    final LocalDate to = order.getTo();

    if (from.isAfter(to)) {
      LOGGER.error(INVALID_ORDER_DATES_LOG, order);
      throw new IllegalArgumentException(environment.getProperty(INVALID_ORDER_DATES));
    }

    if (orderDao.existsByFromAndTo(from, to)) {
      LOGGER.error(INTERSECTING_DATES_LOG, order);
      throw new IllegalArgumentException(environment.getProperty(INTERSECTING_DATES));
    }
  }
}

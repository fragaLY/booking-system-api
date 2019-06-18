package by.vk.bookingsystem.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import by.vk.bookingsystem.controller.HomeController;
import by.vk.bookingsystem.controller.OrderController;
import by.vk.bookingsystem.converter.OrderConverter;
import by.vk.bookingsystem.dao.OrderDao;
import by.vk.bookingsystem.domain.Order;
import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.dto.order.OrderSetDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.CostCalculatorService;
import by.vk.bookingsystem.service.OrderService;
import by.vk.bookingsystem.validator.order.OrderValidator;
import by.vk.bookingsystem.validator.order.impl.OrderValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * The service implementation for {@link OrderDto}
 *
 * @author Vadzim_Kavalkou
 */
@Service
@PropertySources(@PropertySource("classpath:i18n/validation_errors.properties"))
public class OrderServiceImpl implements OrderService {

  private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

  private static final String ORDER_NOT_FOUND = "order.not.found";
  private static final String ORDER_NOT_FOUND_LOG = "The order with id {} was not found.";

  private final OrderDao orderDao;
  private final OrderConverter orderConverter;
  private final OrderValidator orderValidator;
  private final CostCalculatorService costCalculator;
  private final Environment environment;

  /**
   * The constructor with parameters.
   *
   * @param orderDao - {@link OrderDao}
   * @param orderConverter - {@link OrderConverter}
   * @param orderValidator - {@link OrderValidator}
   * @param costCalculator - {@link CostCalculatorService}
   * @param environment - {@link Environment}
   */
  @Autowired
  public OrderServiceImpl(
      final OrderDao orderDao,
      final OrderConverter orderConverter,
      final OrderValidator orderValidator,
      final CostCalculatorService costCalculator,
      final Environment environment) {
    this.orderDao = orderDao;
    this.orderConverter = orderConverter;
    this.orderValidator = orderValidator;
    this.costCalculator = costCalculator;
    this.environment = environment;
  }

  /**
   * Finds orders in between selected dates
   *
   * @param from {@link LocalDate}
   * @param to {@link LocalDate}
   * @return {@link OrderSetDto}
   */
  @Override
  @Cacheable(value = "orders", key = "{#from, #to}")
  public OrderSetDto findAllOrdersBetweenDates(LocalDate from, LocalDate to) {

    if (from.isAfter(to)) {
      LOGGER.warn(OrderValidatorImpl.INVALID_DATES_LOG, from, to);
      throw new IllegalArgumentException(environment.getProperty(OrderValidatorImpl.INVALID_DATES));
    }

    final Set<OrderDto> orderSet =
        orderDao.findAllOrdersBetweenDates(from, to).stream()
            .filter(Objects::nonNull)
            .map(orderConverter::convertToDto)
            .collect(Collectors.toSet());

    final OrderSetDto orderSetDto = new OrderSetDto(orderSet);
    orderSetDto.add(new Link(linkTo(OrderController.class).toString()).withSelfRel());

    return orderSetDto;
  }

  /**
   * Finds the order by its id.
   *
   * <p>If entity with current id is not in the system throws the {@link ObjectNotFoundException}
   *
   * @param id - the id of order
   * @return {@link OrderDto}
   */
  @Cacheable(value = "order", key = "#id")
  @Override
  public OrderDto findOrderById(final String id) {

    if (!orderDao.existsById(id)) {
      LOGGER.warn(ORDER_NOT_FOUND_LOG, id);
      throw new ObjectNotFoundException(environment.getProperty(ORDER_NOT_FOUND));
    }

    final OrderDto order = orderConverter.convertToDto(orderDao.findOrderById(id));
    final List<Link> homeLinks =
        order.getHomes().stream()
            .map(
                home ->
                    linkTo(HomeController.class)
                        .slash(home.getHomeId())
                        .withRel("home_" + home.getName().toLowerCase()))
            .collect(Collectors.toList());

    order.add(homeLinks);

    return order;
  }

  /**
   * Creates the order and returns its id
   *
   * @param order - {@link OrderDto}
   * @return {@link String}
   */
  @CacheEvict(value = "orders", allEntries = true)
  @Override
  public String createOrder(final OrderDto order) {
    orderValidator.validateOwner(order.getOwner());
    orderValidator.validateHomes(order.getHomes());
    orderValidator.validateOrderDates(order);
    order.setCost(costCalculator.calculateCost(order.getFrom(), order.getTo(), order.getGuests()));
    return orderDao.save(orderConverter.convertToEntity(order)).getId().toHexString();
  }

  /**
   * Enriches the order with new information from data transfer object and updates it.
   *
   * <p>If entity with current id is not in the system throws the {@link ObjectNotFoundException}
   *
   * @param dto - {@link OrderDto}
   * @param id - the id of order.
   */
  @Caching(
      evict = {
        @CacheEvict(value = "orders", allEntries = true),
        @CacheEvict(value = "order", key = "#id")
      })
  @Override
  public void updateOrder(final OrderDto dto, final String id) {

    orderValidator.validateOwner(dto.getOwner());
    orderValidator.validateHomes(dto.getHomes());
    orderValidator.validateOrderDates(dto);

    if (!orderDao.existsById(id)) {
      LOGGER.warn(ORDER_NOT_FOUND_LOG, id);
      throw new ObjectNotFoundException(ORDER_NOT_FOUND);
    }

    final Order order = orderDao.findOrderById(id);

    dto.setCost(costCalculator.calculateCost(dto.getFrom(), dto.getTo(), dto.getGuests()));
    orderDao.save(orderConverter.enrichModel(order, dto));
  }

  /**
   * Deletes order by its id.
   *
   * <p>If entity with current id is not in the system throws the {@link ObjectNotFoundException}
   *
   * @param id - the id of order
   */
  @Caching(
      evict = {
        @CacheEvict(value = "orders", allEntries = true),
        @CacheEvict(value = "order", key = "#id")
      })
  @Override
  public void deleteOrderById(final String id) {

    if (!orderDao.existsById(id)) {
      LOGGER.warn(ORDER_NOT_FOUND_LOG, id);
      throw new ObjectNotFoundException(ORDER_NOT_FOUND);
    }

    orderDao.deleteById(id);
  }
}

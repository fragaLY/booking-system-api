package by.vk.bookingsystem.service.impl;

import java.util.Objects;
import java.util.stream.Collectors;

import by.vk.bookingsystem.converter.OrderConverter;
import by.vk.bookingsystem.dao.OrderMongoDao;
import by.vk.bookingsystem.domain.Order;
import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.dto.order.OrderSetDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.CostCalculatorService;
import by.vk.bookingsystem.service.OrderService;
import by.vk.bookingsystem.validator.order.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@PropertySources(@PropertySource("classpath:i18n/validation_errors.properties"))
public class OrderServiceMongoImpl implements OrderService {

  private static final String ORDER_NOT_FOUND = "order.not.found";

  private final OrderMongoDao orderDao;
  private final OrderConverter orderConverter;
    private final OrderValidator orderValidator;
    private final CostCalculatorService costCalculator;

  private final Environment environment;

  @Autowired
  public OrderServiceMongoImpl(
          final OrderMongoDao orderDao,
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

  @Override
  public OrderSetDto findAllOrders() {
    return new OrderSetDto(
        orderDao.findAll().stream()
            .filter(Objects::nonNull)
            .map(orderConverter::convertToDto)
            .collect(Collectors.toSet()));
  }

  @Override
  public OrderDto findOrderById(final String id) {
    final Order order = orderDao.findOrderById(id);

    if (order == null) {
      throw new ObjectNotFoundException(environment.getProperty(ORDER_NOT_FOUND));
    }

    return orderConverter.convertToDto(order);
  }

    @Override
  public String createOrder(final OrderDto dto) {
        orderValidator.validateOwner(dto.getOwner());
        orderValidator.validateHomes(dto.getHomes());
        orderValidator.validateOrderDates(dto);
        dto.setCost(costCalculator.calculateCost(dto));
    return orderDao.save(orderConverter.convertToEntity(dto)).getId().toHexString();
  }

  @Override
  public void updateOrder(final OrderDto dto, final String id) {

    final Order order = orderDao.findOrderById(id);

    if (order == null) {
      throw new ObjectNotFoundException(ORDER_NOT_FOUND);
    }

      orderValidator.validateOwner(dto.getOwner());
      orderValidator.validateHomes(dto.getHomes());
      orderValidator.validateOrderDates(dto);
      dto.setCost(costCalculator.calculateCost(dto));
      orderDao.save(orderConverter.enrichModel(order, dto)).getId().toHexString();
  }

  @Override
  public void deleteOrderById(final String id) {

    if (orderDao.findOrderById(id) == null) {
      throw new ObjectNotFoundException(ORDER_NOT_FOUND);
    }

    orderDao.deleteById(id);
  }
}

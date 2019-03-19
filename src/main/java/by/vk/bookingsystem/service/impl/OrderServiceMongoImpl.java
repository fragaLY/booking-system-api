package by.vk.bookingsystem.service.impl;

import java.util.Objects;
import java.util.stream.Collectors;

import by.vk.bookingsystem.converter.OrderConverter;
import by.vk.bookingsystem.dao.OrderMongoDao;
import by.vk.bookingsystem.domain.Order;
import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.dto.order.OrderSetDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceMongoImpl implements OrderService {

  private static final String ORDER_NOT_FOUND = "not.found.order";

  private final OrderMongoDao orderDao;
  private final OrderConverter orderConverter;
  private final Environment environment;

  @Autowired
  public OrderServiceMongoImpl(
      final OrderMongoDao orderDao,
      final OrderConverter orderConverter,
      final Environment environment) {
    this.orderDao = orderDao;
    this.orderConverter = orderConverter;
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
  public String createOrder(OrderDto dto) {
    return null;
  }

  @Override
  public String updateOrder(OrderDto dto, String id) {
    return null;
  }

  @Override
  public boolean deleteOrderById(String id) {
    return false;
  }
}

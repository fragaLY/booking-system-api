package by.vk.bookingsystem.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import by.vk.bookingsystem.converter.OrderConverter;
import by.vk.bookingsystem.dao.OrderMongoDao;
import by.vk.bookingsystem.domain.Order;
import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.dto.order.OrderSetDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.exception.order.IntercesctionDatesException;
import by.vk.bookingsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceMongoImpl implements OrderService {

  private static final String ORDER_NOT_FOUND = "order.not.found";
  private static final String INTERSECTING_DATES = "order.dates.intersection";

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

  @Override //todo vk: handle intersection of dates
  public String createOrder(final OrderDto dto) {
    final List<Order> intersecting =
        orderDao.findOrdersByFromBetweenAndToBetween(dto.getFrom(), dto.getTo());

    if (intersecting != null && !intersecting.isEmpty()) {
      throw new IntercesctionDatesException(INTERSECTING_DATES);
    }

    return orderDao.save(orderConverter.convertToEntity(dto)).getId().toHexString();
  }

  @Override
  public String updateOrder(final OrderDto dto, final String id) {
    final Order order = orderDao.findOrderById(id);

    if (order == null) {
      throw new ObjectNotFoundException(ORDER_NOT_FOUND);
    }

    return orderDao.save(orderConverter.enrichModel(order, dto)).getId().toHexString();
  }

  @Override
  public boolean deleteOrderById(final String id) {

    if (orderDao.findOrderById(id) == null) {
      throw new ObjectNotFoundException(ORDER_NOT_FOUND);
    }

    orderDao.deleteById(id);
    return true;
  }
}

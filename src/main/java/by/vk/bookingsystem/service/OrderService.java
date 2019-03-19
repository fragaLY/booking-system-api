package by.vk.bookingsystem.service;

import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.dto.order.OrderSetDto;

public interface OrderService {

  OrderSetDto findAllOrders();

  OrderDto findOrderById(String id);

  String createOrder(OrderDto dto);

  String updateOrder(OrderDto dto, String id);

  boolean deleteOrderById(String id);
}

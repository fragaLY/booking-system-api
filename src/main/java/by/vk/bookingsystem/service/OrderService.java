package by.vk.bookingsystem.service;

import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.dto.order.OrderSetDto;

public interface OrderService {

  OrderSetDto findAllOrders();

  OrderDto findOrderById(String id);

  String createOrder(OrderDto dto);

    void updateOrder(OrderDto dto, String id);

    void deleteOrderById(String id);
}

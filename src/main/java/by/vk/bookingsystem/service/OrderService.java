package by.vk.bookingsystem.service;

import by.vk.bookingsystem.domain.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The service for orders
 *
 * @author Vadzim_Kavalkou
 */
public interface OrderService {

  Flux<Order> findAllOrders();

  Mono<Order> findOrderById(String id);

  Mono<Void> createOrder(Order order);

  Mono<Void> updateOrder(Order order, String id);

  Mono<Void> deleteOrderById(String id);
}

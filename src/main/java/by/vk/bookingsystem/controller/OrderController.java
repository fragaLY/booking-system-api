package by.vk.bookingsystem.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.dto.order.OrderSetDto;
import by.vk.bookingsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * The controller to work with orders
 *
 * @author Vadzim_Kavalkou
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;

  /**
   * The constructor of class. Uses autowiring via it.
   *
   * @param orderService - the service with business logic to work with orders
   */
  @Autowired
  public OrderController(final OrderService orderService) {
    this.orderService = orderService;
  }

  /**
   * Returns the set of all orders.
   *
   * @return {@link ResponseEntity}
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<OrderSetDto> getAllOrders() {
    return ResponseEntity.ok(orderService.findAllOrders());
  }

  /**
   * Returns the order by its id.
   *
   * @param id - the id of order
   * @return {@link ResponseEntity}
   */
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<OrderDto> getOrder(
      @NotBlank(message = "The id cannot be blank") @PathVariable(value = "id") final String id) {
    return ResponseEntity.ok(orderService.findOrderById(id));
  }

  /**
   * Creates the order.
   *
   * @param dto - the order
   * @return {@link ResponseEntity}
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<Void> createOrder(
      @NotNull(message = "The order cannot be null") @Valid @RequestBody final OrderDto dto) {

    final URI uri =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(orderService.createOrder(dto))
            .toUri();

    return ResponseEntity.created(uri).build();
  }

  /**
   * Updates the order with new information.
   *
   * @param dto - the order
   * @param id - the id of order
   * @return {@link ResponseEntity}
   */
  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<Void> updateOrder(
      final HttpServletRequest request,
      @NotNull(message = "The order cannot be null") @Valid @RequestBody final OrderDto dto,
      @NotBlank(message = "The id cannot be blank") @PathVariable final String id)
      throws URISyntaxException {
    orderService.updateOrder(dto, id);
    return ResponseEntity.noContent().location(new URI(request.getRequestURI())).build();
  }

  /**
   * Deletes the order by its id.
   *
   * @param id - the id of order
   * @return {@link ResponseEntity}
   */
  @DeleteMapping(value = "/{id}")
  @ResponseBody
  public ResponseEntity<Void> deleteOrder(@NotBlank @PathVariable final String id) {
    orderService.deleteOrderById(id);
    return ResponseEntity.noContent().build();
  }
}

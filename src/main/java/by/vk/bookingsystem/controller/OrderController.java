package by.vk.bookingsystem.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.dto.order.OrderSetDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * The controller to work with orders
 *
 * @author Vadzim_Kavalkou
 */
@CrossOrigin(
    maxAge = 3600,
    origins = "*",
    methods = {
      RequestMethod.GET,
      RequestMethod.POST,
      RequestMethod.PUT,
      RequestMethod.DELETE,
      RequestMethod.OPTIONS,
      RequestMethod.HEAD
    })
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
   * Returns the options
   *
   * @return {@link ResponseEntity}
   */
  @ApiOperation(value = "Get options", notes = "The options will be returned")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Options were retrieved"),
        @ApiResponse(
            code = 400,
            message = "Bad request",
            response = IllegalArgumentException.class),
        @ApiResponse(code = 401, message = "Unauthorized client"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(
            code = 404,
            message = "Options were not found",
            response = ObjectNotFoundException.class),
        @ApiResponse(code = 500, message = "Internal Error")
      })
  @RequestMapping(value = "**", method = RequestMethod.OPTIONS)
  @ResponseBody
  public ResponseEntity<Void> getOptions() {
    return ResponseEntity.ok()
        .allow(
            HttpMethod.GET,
            HttpMethod.POST,
            HttpMethod.PUT,
            HttpMethod.DELETE,
            HttpMethod.OPTIONS,
            HttpMethod.HEAD)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
        .cacheControl(CacheControl.maxAge(3600, TimeUnit.SECONDS))
        .build();
  }

  /**
   * Returns the page with orders between selected dates
   *
   * <p>By default the date frame is the last month
   *
   * @param pageable {@link Pageable}
   * @return {@link ResponseEntity}
   */
  @ApiOperation(
      value = "Get all orders",
      notes =
          "Orders will be sent in the location response page with default size = 10, page = 0, sorting by order date from. Also, you are able to find links for next or previous page if they needed",
      response = OrderSetDto.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Orders were retrieved", response = OrderSetDto.class),
        @ApiResponse(
            code = 400,
            message = "Bad request",
            response = IllegalArgumentException.class),
        @ApiResponse(code = 401, message = "Unauthorized client"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(
            code = 404,
            message = "Orders were not found",
            response = ObjectNotFoundException.class),
        @ApiResponse(code = 500, message = "Internal Error")
      })
  @GetMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
  @ResponseBody
  public ResponseEntity<OrderSetDto> getAllOrders(
      @ApiParam("RequestParam: ?page=XXX&size=YYY&sort=ZZZ") @PageableDefault(sort = "from")
          final Pageable pageable,
      @ApiParam("The start date of searching for orders. Date format yyyy-MM-dd")
          @RequestParam("from")
          @DateTimeFormat(pattern = "yyyy-MM-dd")
          final LocalDate from,
      @ApiParam("The end date of searching for orders. Date format yyyy-MM-dd")
          @RequestParam("to")
          @DateTimeFormat(pattern = "yyyy-MM-dd")
          final LocalDate to) {
    return ResponseEntity.ok(orderService.findAllOrdersBetweenDates(pageable, from, to));
  }

  /**
   * Returns the order by its id.
   *
   * @param id - the id of order
   * @return {@link ResponseEntity}
   */
  @ApiOperation(
      value = "Get order by id",
      notes = "Order will be sent in the location response",
      response = OrderDto.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Order was retrieved", response = OrderDto.class),
        @ApiResponse(
            code = 400,
            message = "Bad request",
            response = IllegalArgumentException.class),
        @ApiResponse(code = 401, message = "Unauthorized client"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(
            code = 404,
            message = "Order was not found",
            response = ObjectNotFoundException.class),
        @ApiResponse(code = 500, message = "Internal Error")
      })
  @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
  @ResponseBody
  public ResponseEntity<OrderDto> getOrder(
      @NotBlank(message = "The id cannot be blank") @PathVariable(value = "id") final String id) {
    final OrderDto order = orderService.findOrderById(id);

    final List<Link> homeLinks =
        order.getHomes().stream()
            .map(
                home ->
                    linkTo(HomeController.class)
                        .slash(home.getHomeId())
                        .withRel("home_" + home.getName().toLowerCase()))
            .collect(Collectors.toList());

    order.add(homeLinks);

    return ResponseEntity.ok(order);
  }

  /**
   * Creates the order.
   *
   * @param dto - the order
   * @return {@link ResponseEntity}
   */
  @ApiResponses(
      value = {
        @ApiResponse(code = 201, message = "Order created"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized client"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Error getting orders")
      })
  @PostMapping(consumes = MediaTypes.HAL_JSON_UTF8_VALUE)
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
   * @param request - the request
   * @param dto - the order
   * @param id - the id of order
   * @return {@link ResponseEntity}
   * @throws URISyntaxException throws
   */
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Order was updated"),
        @ApiResponse(
            code = 400,
            message = "Bad request",
            response = IllegalArgumentException.class),
        @ApiResponse(code = 401, message = "Unauthorized client"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Internal Error")
      })
  @PutMapping(value = "/{id}", consumes = MediaTypes.HAL_JSON_UTF8_VALUE)
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
  @ApiResponses(
      value = {
        @ApiResponse(code = 204, message = "Order deleted"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized client"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Internal Error")
      })
  @DeleteMapping(value = "/{id}")
  @ResponseBody
  public ResponseEntity<Void> deleteOrder(
      @NotBlank(message = "The id cannot be blank") @PathVariable final String id) {
    orderService.deleteOrderById(id);
    return ResponseEntity.noContent().build();
  }
}

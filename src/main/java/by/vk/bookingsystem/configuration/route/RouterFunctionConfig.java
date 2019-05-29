package by.vk.bookingsystem.configuration.route;

import by.vk.bookingsystem.domain.Home;
import by.vk.bookingsystem.domain.Order;
import by.vk.bookingsystem.domain.Price;
import by.vk.bookingsystem.domain.User;
import by.vk.bookingsystem.service.HomeService;
import by.vk.bookingsystem.service.OrderService;
import by.vk.bookingsystem.service.PriceService;
import by.vk.bookingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class RouterFunctionConfig {

  private final HomeService homeService;
  private final PriceService priceService;
  private final UserService userService;
  private final OrderService orderService;

  @Autowired
  public RouterFunctionConfig(
      final HomeService homeService,
      final PriceService priceService,
      final UserService userService,
      final OrderService orderService) {
    this.homeService = homeService;
    this.priceService = priceService;
    this.userService = userService;
    this.orderService = orderService;
  }

  @Bean
  public RouterFunction<ServerResponse> homeRouterFunction() {
    return route(
            GET("/homes").and(accept(APPLICATION_JSON)),
            request -> ok().body(homeService.findAllHomes(), Home.class))
        .andRoute(
            GET("/homes/{id}").and(accept(APPLICATION_JSON)),
            request -> ok().body(homeService.findHomeById(request.pathVariable("id")), Home.class));
  }

  @Bean
  public RouterFunction<ServerResponse> priceRouterFunction() {
    return route(
            GET("/prices").and(accept(APPLICATION_JSON)),
            request -> ok().body(priceService.findAllPrices(), Price.class))
        .andRoute(
            GET("/prices/{id}").and(accept(APPLICATION_JSON)),
            request ->
                ok().body(priceService.findPriceById(request.pathVariable("id")), Price.class));
  }

  @Bean
  public RouterFunction<ServerResponse> userRouterFunction() {
    return route(
            GET("/users").and(accept(APPLICATION_JSON)),
            request -> ok().body(userService.findAllUsers(), User.class))
        .andRoute(
            GET("/users/{id}").and(accept(APPLICATION_JSON)),
            request -> ok().body(userService.findUserById(request.pathVariable("id")), User.class))
        .andRoute(
            POST("/users").and(contentType(APPLICATION_JSON)),
            request ->
                request
                    .bodyToMono(User.class)
                    .doOnNext(userService::createUser)
                    .then(noContent().build()))
        .andRoute(
            PUT("/users/{id}").and(contentType(APPLICATION_JSON)),
            request ->
                request
                    .bodyToMono(User.class)
                    .doOnNext(user -> userService.updateUser(user, request.pathVariable("id")))
                    .then(noContent().build()))
        .andRoute(
            DELETE("/users/{id}"),
            request ->
                userService.deleteUserById(request.pathVariable("id")).then(noContent().build()));
  }

  @Bean
  public RouterFunction<ServerResponse> ordersRouterFunction() {
    return route(
            GET("/orders").and(accept(APPLICATION_JSON)),
            request -> ok().body(orderService.findAllOrders(), Order.class))
        .andRoute(
            GET("/orders/{id}").and(accept(APPLICATION_JSON)),
            request ->
                ok().body(orderService.findOrderById(request.pathVariable("id")), Order.class))
        .andRoute(
            POST("/orders").and(contentType(APPLICATION_JSON)),
            request ->
                request
                    .bodyToMono(Order.class)
                    .doOnNext(orderService::createOrder)
                    .then(noContent().build()))
        .andRoute(
            PUT("/orders/{id}").and(contentType(APPLICATION_JSON)),
            request ->
                request
                    .bodyToMono(Order.class)
                    .doOnNext(order -> orderService.updateOrder(order, request.pathVariable("id")))
                    .then(noContent().build()))
        .andRoute(
            DELETE("/orders/{id}"),
            request ->
                orderService.deleteOrderById(request.pathVariable("id")).then(noContent().build()));
  }
}

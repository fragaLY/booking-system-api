package by.vk.bookingsystem.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import by.vk.bookingsystem.domain.User;
import by.vk.bookingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The controller to work with users
 *
 * @author Vadzim_Kavalkou
 */
@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(final UserService userService) {
    this.userService = userService;
  }

  @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
  @ResponseStatus(HttpStatus.FOUND)
  public Mono<User> getUserById(
      @NotBlank(message = "The id cannot be blank") @PathVariable(value = "id") final String id) {
    return userService.findUserById(id);
  }

  @GetMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
  @ResponseStatus(HttpStatus.FOUND)
  public Flux<User> getUsers() {
    return userService.findAllUsers();
  }

  @PostMapping(consumes = MediaTypes.HAL_JSON_UTF8_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<User> createUser(
      @NotNull(message = "The user cannot be null") @Valid @RequestBody final User user) {
    return userService.createUser(user);
  }

  @PutMapping(value = "/{id}", consumes = MediaTypes.HAL_JSON_UTF8_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public Flux<User> updateUser(
      @NotNull(message = "The user cannot be null") @Valid @RequestBody final User user,
      @NotBlank(message = "The id cannot be blank") @PathVariable final String id) {
    return userService.updateUser(user, id);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Void> deleteUser(
      @NotBlank(message = "The id cannot be blank") @PathVariable final String id) {
    return userService.deleteUserById(id);
  }
}

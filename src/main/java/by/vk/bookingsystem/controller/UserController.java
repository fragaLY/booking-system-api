package by.vk.bookingsystem.controller;

import java.net.URI;
import java.util.List;
import java.util.Set;

import by.vk.bookingsystem.dto.user.UserDto;
import by.vk.bookingsystem.service.UserService;
import by.vk.bookingsystem.validator.user.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;
  private final UserValidator userValidator;

  @Autowired
  public UserController(final UserService userService, final UserValidator userValidator) {
    this.userService = userService;
    this.userValidator = userValidator;
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<?> getUser(
      final HttpRequest request, @PathVariable(value = "id") final String userId) {

    final UserDto dto = userService.findUserById(userId);
    final Set<String> errorMessages = userValidator.validate(dto);
    final HttpHeaders headers = request.getHeaders();

    return errors.isEmpty()
        ? new ResponseEntity<>(dto, headers, HttpStatus.FOUND)
        : new ResponseEntity<>(errors, headers, HttpStatus.BAD_REQUEST);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<List<UserDto>> getUsers() {
    return ResponseEntity.ok(userService.findAllUsers());
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<Void> createUser(@RequestBody final UserDto userDto) {

    final URI uri =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(userService.saveOrUpdateUser(userDto, null))
            .toUri();

    return ResponseEntity.created(uri).build();
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<Void> updateUser(
      final HttpRequest request,
      @RequestBody final UserDto userDto,
      @PathVariable final String id) {
    userService.saveOrUpdateUser(userDto, id);
    return ResponseEntity.noContent().location(request.getURI()).build();
  }

  @DeleteMapping
  @ResponseBody
  public ResponseEntity<Void> deleteUser(@PathVariable String id) {
    userService.deleteUserById(id);
    return ResponseEntity.noContent().build();
  }
}

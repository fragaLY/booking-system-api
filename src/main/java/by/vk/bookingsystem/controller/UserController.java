package by.vk.bookingsystem.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import by.vk.bookingsystem.dto.user.UserDto;
import by.vk.bookingsystem.dto.user.UserSetDto;
import by.vk.bookingsystem.service.UserService;
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

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(final UserService userService) {
    this.userService = userService;
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<UserDto> getUser(
      @NotBlank(message = "The id cannot be blank") @PathVariable(value = "id") final String id) {
    return ResponseEntity.ok(userService.findUserById(id));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<UserSetDto> getUsers() {
    return ResponseEntity.ok(userService.findAllUsers());
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<Void> createUser(
      @NotNull(message = "The user cannot be null") @Valid @RequestBody final UserDto dto) {

    final URI uri =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(userService.createUser(dto))
            .toUri();

    return ResponseEntity.created(uri).build();
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<Void> updateUser(
      final HttpServletRequest request,
      @NotNull(message = "The user cannot be null") @Valid @RequestBody final UserDto dto,
      @NotBlank(message = "The id cannot be blank") @PathVariable final String id)
      throws URISyntaxException {
    userService.updateUser(dto, id);
    return ResponseEntity.noContent().location(new URI(request.getRequestURI())).build();
  }

  @DeleteMapping(value = "/{id}")
  @ResponseBody
  public ResponseEntity<Void> deleteUser(@NotBlank @PathVariable final String id) {
    userService.deleteUserById(id);
    return ResponseEntity.noContent().build();
  }
}

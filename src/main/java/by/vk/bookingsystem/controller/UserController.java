package by.vk.bookingsystem.controller;

import by.vk.bookingsystem.dto.user.UserDto;
import by.vk.bookingsystem.dto.user.UserSetDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
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

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  /**
   * The constructor of class. Uses autowiring via it.
   *
   * @param userService - the service with business logic to work with users
   */
  @Autowired
  public UserController(final UserService userService) {
    this.userService = userService;
  }

  /**
   * Returns the user by its id.
   *
   * @param id - the id of user
   * @return {@link ResponseEntity}
   */
  @ApiOperation(
      value = "Get user by id",
      notes = "User will be sent in the location response",
      response = UserDto.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "User was retrieved", response = UserDto.class),
        @ApiResponse(
            code = 400,
            message = "Bad request",
            response = IllegalArgumentException.class),
        @ApiResponse(code = 401, message = "Unauthorized client"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(
            code = 404,
            message = "User was not found",
            response = ObjectNotFoundException.class),
        @ApiResponse(code = 500, message = "Internal Error")
      })
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public ResponseEntity<UserDto> getUserById(
      @NotBlank(message = "The id cannot be blank") @PathVariable(value = "id") final String id) {

    final UserDto user = userService.findUserById(id);
    final Link selfRel = linkTo(UserController.class).slash(id).withSelfRel();
    user.add(selfRel);
    return ResponseEntity.ok(user);
  }

  /**
   * Returns the set of all users
   *
   * @return {@link ResponseEntity}
   */
  @ApiOperation(
      value = "Get all users",
      notes = "Users will be sent in the location response",
      response = UserSetDto.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Users were retrieved", response = UserSetDto.class),
        @ApiResponse(
            code = 400,
            message = "Bad request",
            response = IllegalArgumentException.class),
        @ApiResponse(code = 401, message = "Unauthorized client"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(
            code = 404,
            message = "User was not found",
            response = ObjectNotFoundException.class),
        @ApiResponse(code = 500, message = "Internal Error")
      })
  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public ResponseEntity<UserSetDto> getUsers() {
    return ResponseEntity.ok(userService.findAllUsers());
  }

  /**
   * Creates the user.
   *
   * @param dto - the user
   * @return {@link ResponseEntity}
   */
  @ApiResponses(
      value = {
        @ApiResponse(code = 201, message = "User created"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized client"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Internal Error")
      })
  @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
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

  /**
   * Updates the user with new information.
   *
   * @param request - the request
   * @param dto - the user
   * @param id - the id of user
   * @return {@link ResponseEntity}
   * @throws URISyntaxException the exception of URI syntax
   */
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "User updated"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized client"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Internal Error")
      })
  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public ResponseEntity<Void> updateUser(
      final HttpServletRequest request,
      @NotNull(message = "The user cannot be null") @Valid @RequestBody final UserDto dto,
      @NotBlank(message = "The id cannot be blank") @PathVariable final String id)
      throws URISyntaxException {
    userService.updateUser(dto, id);
    return ResponseEntity.noContent().location(new URI(request.getRequestURI())).build();
  }

  /**
   * Deletes user by id
   *
   * @param id - the id of user
   * @return {@link ResponseEntity}
   */
  @ApiResponses(
      value = {
        @ApiResponse(code = 204, message = "User deleted"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized client"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Internal Error")
      })
  @DeleteMapping(value = "/{id}")
  @ResponseBody
  public ResponseEntity<Void> deleteUser(@NotBlank @PathVariable final String id) {
    userService.deleteUserById(id);
    return ResponseEntity.noContent().build();
  }
}

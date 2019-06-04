package by.vk.bookingsystem.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import by.vk.bookingsystem.dto.user.UserDto;
import by.vk.bookingsystem.dto.user.UserSetDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.UserService;
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
 * The controller to work with users
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
  @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
  @ResponseBody
  public ResponseEntity<UserDto> getUserById(
      @NotBlank(message = "The id cannot be blank") @PathVariable(value = "id") final String id) {

    final UserDto user = userService.findUserById(id);
    final Link selfRel = linkTo(UserController.class).slash(id).withSelfRel();
    user.add(selfRel);
    return ResponseEntity.ok(user);
  }

  /**
   * Returns the page with users
   *
   * @param pageable {@link Pageable}
   * @param from {@link LocalDate}
   * @param to {@link LocalDate}
   * @return {@link ResponseEntity}
   */
  @ApiOperation(
      value = "Get all users",
      notes =
          "Users will be sent in the location response page with default size = 10, page = 0, sorting by user registration date. Also, you are able to find links for next or previous page if they needed",
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
  @GetMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
  @ResponseBody
  public ResponseEntity<UserSetDto> getAllUsers(
      @ApiParam("RequestParam: ?page=XXX&size=YYY&sort=ZZZ") @PageableDefault(sort = "registered")
          final Pageable pageable,
      @ApiParam(
              value = "The start date of searching for orders. Date format yyyy-MM-dd",
              defaultValue = "0001-01-01")
          @RequestParam(value = "from", defaultValue = "0001-01-01")
          @DateTimeFormat(pattern = "yyyy-MM-dd")
          final LocalDate from,
      @ApiParam(
              value = "The end date of searching for orders. Date format yyyy-MM-dd",
              defaultValue = "9999-12-31")
          @RequestParam(value = "to", defaultValue = "9999-12-31")
          @DateTimeFormat(pattern = "yyyy-MM-dd")
          final LocalDate to) {

    return ResponseEntity.ok(userService.findAllUsersBetweenDates(pageable, from, to));
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
  @PostMapping(consumes = MediaTypes.HAL_JSON_UTF8_VALUE)
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
  @PutMapping(value = "/{id}", consumes = MediaTypes.HAL_JSON_UTF8_VALUE)
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
  public ResponseEntity<Void> deleteUser(
      @NotBlank(message = "The id cannot be blank") @PathVariable final String id) {
    userService.deleteUserById(id);
    return ResponseEntity.noContent().build();
  }
}

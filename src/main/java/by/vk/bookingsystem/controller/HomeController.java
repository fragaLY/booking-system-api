package by.vk.bookingsystem.controller;

import java.util.concurrent.TimeUnit;
import javax.validation.constraints.NotBlank;

import by.vk.bookingsystem.dto.home.HomeDto;
import by.vk.bookingsystem.dto.home.HomeSetDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.HomeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * The controller to work with homes
 *
 * @author Vadzim_Kavalkou
 */
@CrossOrigin(
    maxAge = 3600,
    origins = "*",
    methods = {RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.HEAD})
@RestController
@RequestMapping("/homes")
public class HomeController {

  private final HomeService homeService;

  /**
   * The constructor of class. Uses autowiring via it.
   *
   * @param homeService - the service with business logic to work with homes
   */
  @Autowired
  public HomeController(final HomeService homeService) {
    this.homeService = homeService;
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
        .allow(HttpMethod.GET, HttpMethod.OPTIONS, HttpMethod.HEAD)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .cacheControl(CacheControl.maxAge(3600, TimeUnit.SECONDS))
        .build();
  }

  /**
   * Returns the set of all homes
   *
   * @return {@link ResponseEntity}
   */
  @ApiOperation(
      value = "Get all homes",
      notes = "Homes will be sent in the location response",
      response = HomeSetDto.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Homes were retrieved", response = HomeSetDto.class),
        @ApiResponse(
            code = 400,
            message = "Bad request",
            response = IllegalArgumentException.class),
        @ApiResponse(code = 401, message = "Unauthorized client"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(
            code = 404,
            message = "Homes were not found",
            response = ObjectNotFoundException.class),
        @ApiResponse(code = 500, message = "Internal Error")
      })
  @GetMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
  @ResponseBody
  public ResponseEntity<HomeSetDto> getHomes() {
    return ResponseEntity.ok(homeService.findAllHomes());
  }

  /**
   * Returns the home by its id.
   *
   * @param id - the id of home
   * @return {@link ResponseEntity}
   */
  @ApiOperation(
      value = "Get home by id",
      notes = "Home will be sent in the location response",
      response = HomeDto.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Home was retrieved", response = HomeDto.class),
        @ApiResponse(
            code = 400,
            message = "Bad request",
            response = IllegalArgumentException.class),
        @ApiResponse(code = 401, message = "Unauthorized client"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(
            code = 404,
            message = "Home was not found",
            response = ObjectNotFoundException.class),
        @ApiResponse(code = 500, message = "Internal Error")
      })
  @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
  @ResponseBody
  public ResponseEntity<HomeDto> getHomeById(
      @NotBlank(message = "The id cannot be blank") @PathVariable final String id) {
    final HomeDto home = homeService.findHomeById(id);
    final Link selfRel = linkTo(HomeController.class).slash(id).withSelfRel();
    home.add(selfRel);
    return ResponseEntity.ok(home);
  }
}

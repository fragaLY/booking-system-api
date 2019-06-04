package by.vk.bookingsystem.controller;

import java.util.concurrent.TimeUnit;
import javax.validation.constraints.NotBlank;

import by.vk.bookingsystem.dto.price.PriceDto;
import by.vk.bookingsystem.dto.price.PriceSetDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.PriceService;
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
 * The controller to work with prices
 *
 * @author Vadzim_Kavalkou
 */
@CrossOrigin(
    maxAge = 3600,
    origins = "*",
    methods = {RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.HEAD})
@RestController
@RequestMapping("/prices")
public class PriceController {

  private final PriceService priceService;

  /**
   * The constructor of class. Uses autowiring via it.
   *
   * @param priceService - the service with business logic to work with prices
   */
  @Autowired
  public PriceController(final PriceService priceService) {
    this.priceService = priceService;
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
   * Returns the set of all prices
   *
   * @return {@link ResponseEntity}
   */
  @ApiOperation(
      value = "Get all prices",
      notes = "Prices will be sent in the location response",
      response = PriceSetDto.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Prices were retrieved", response = PriceSetDto.class),
        @ApiResponse(
            code = 400,
            message = "Bad request",
            response = IllegalArgumentException.class),
        @ApiResponse(code = 401, message = "Unauthorized client"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(
            code = 404,
            message = "Prices were not found",
            response = ObjectNotFoundException.class),
        @ApiResponse(code = 500, message = "Internal Error")
      })
  @GetMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
  @ResponseBody
  public ResponseEntity<PriceSetDto> getPrices() {
    return ResponseEntity.ok(priceService.findAllPrices());
  }

  /**
   * Returns the price by its id.
   *
   * @param id - the id of price
   * @return {@link ResponseEntity}
   */
  @ApiOperation(
      value = "Get price by id",
      notes = "Price will be sent in the location response",
      response = PriceDto.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Price was retrieved", response = PriceDto.class),
        @ApiResponse(
            code = 400,
            message = "Bad request",
            response = IllegalArgumentException.class),
        @ApiResponse(code = 401, message = "Unauthorized client"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(
            code = 404,
            message = "Price was not found",
            response = ObjectNotFoundException.class),
        @ApiResponse(code = 500, message = "Internal Error")
      })
  @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
  @ResponseBody
  public ResponseEntity<PriceDto> getPrice(
      @NotBlank(message = "The id cannot be blank") @PathVariable final String id) {
    final PriceDto price = priceService.findPriceById(id);
    final Link selfRel = linkTo(PriceController.class).slash(id).withSelfRel();
    price.add(selfRel);
    return ResponseEntity.ok(price);
  }
}

package by.vk.bookingsystem.controller;

import javax.validation.constraints.NotBlank;

import by.vk.bookingsystem.dto.price.PriceDto;
import by.vk.bookingsystem.dto.price.PriceSetDto;
import by.vk.bookingsystem.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller to work with prices
 *
 * @author Vadzim_Kavalkou
 */
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
   * Returns the set of all prices
   *
   * @return {@link ResponseEntity}
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<PriceSetDto> getPrices() {
    return ResponseEntity.ok(priceService.findAllPrices());
  }

  /**
   * Returns the price by its id.
   *
   * @param id - the id of home
   * @return {@link ResponseEntity}
   */
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<PriceDto> getPrice(
      @NotBlank(message = "The id cannot be blank") @PathVariable final String id) {
    return ResponseEntity.ok(priceService.findPriceById(id));
  }
}

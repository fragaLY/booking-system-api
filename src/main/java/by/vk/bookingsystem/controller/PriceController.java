package by.vk.bookingsystem.controller;

import javax.validation.constraints.NotBlank;

import by.vk.bookingsystem.domain.Price;
import by.vk.bookingsystem.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The controller to work with prices
 *
 * @author Vadzim_Kavalkou
 */
@RestController
@RequestMapping("/prices")
public class PriceController {

  private final PriceService priceService;

  @Autowired
  public PriceController(final PriceService priceService) {
    this.priceService = priceService;
  }

  @GetMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
  @ResponseStatus(HttpStatus.FOUND)
  public Flux<Price> getPrices() {
    return priceService.findAllPrices();
  }

  @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
  @ResponseStatus(HttpStatus.FOUND)
  public Mono<Price> getPrice(
      @NotBlank(message = "The id cannot be blank") @PathVariable final String id) {
    return priceService.findPriceById(id);
  }
}

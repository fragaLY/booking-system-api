package by.vk.bookingsystem.controller;

import java.util.List;

import javax.validation.constraints.NotBlank;

import by.vk.bookingsystem.dto.price.PriceDto;
import by.vk.bookingsystem.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prices")
public class PriceController {

  private final PriceService priceService;

  @Autowired
  public PriceController(final PriceService priceService) {
    this.priceService = priceService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<List<PriceDto>> getPrices() {
    return ResponseEntity.ok(priceService.findAllPrices());
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<PriceDto> getPrice(@NotBlank(message = "The id cannot be blank") @PathVariable final String id) {
    return ResponseEntity.ok(priceService.findPriceById(id));
  }
}

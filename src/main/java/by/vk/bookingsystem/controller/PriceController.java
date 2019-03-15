package by.vk.bookingsystem.controller;

import java.util.List;

import by.vk.bookingsystem.dto.price.PriceDto;
import by.vk.bookingsystem.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    return new ResponseEntity<>(priceService.findAll(), HttpStatus.FOUND);
  }
}

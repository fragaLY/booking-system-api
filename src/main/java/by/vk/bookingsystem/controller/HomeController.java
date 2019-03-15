package by.vk.bookingsystem.controller;

import java.util.List;

import by.vk.bookingsystem.dto.home.HomeDto;
import by.vk.bookingsystem.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/homes")
public class HomeController {

  private final HomeService homeService;

  @Autowired
  public HomeController(final HomeService homeService) {
    this.homeService = homeService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<List<HomeDto>> getHomes() {
    return new ResponseEntity<>(homeService.findAllHomes(), HttpStatus.FOUND);
  }
}

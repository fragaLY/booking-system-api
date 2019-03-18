package by.vk.bookingsystem.controller;

import java.util.List;

import javax.validation.constraints.NotBlank;

import by.vk.bookingsystem.dto.home.HomeDto;
import by.vk.bookingsystem.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    return ResponseEntity.ok(homeService.findAllHomes());
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<HomeDto> getHome(
      @NotBlank(message = "The id cannot be blank") @PathVariable final String id) {
    return ResponseEntity.ok(homeService.findHomeById(id));
  }
}

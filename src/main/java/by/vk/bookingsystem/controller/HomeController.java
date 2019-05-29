package by.vk.bookingsystem.controller;

import javax.validation.constraints.NotBlank;

import by.vk.bookingsystem.domain.Home;
import by.vk.bookingsystem.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The controller to work with homes
 *
 * @author Vadzim_Kavalkou
 */
@RestController
@RequestMapping("/homes")
public class HomeController {

  private final HomeService homeService;

  @Autowired
  public HomeController(final HomeService homeService) {
    this.homeService = homeService;
  }

  @GetMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
  @ResponseBody
  @ResponseStatus(HttpStatus.FOUND)
  public Flux<Home> getHomes() {
    return homeService.findAllHomes();
  }

  @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
  @ResponseBody
  @ResponseStatus(HttpStatus.FOUND)
  public Mono<Home> getHomeById(
      @NotBlank(message = "The id cannot be blank") @PathVariable final String id) {
    return homeService.findHomeById(id);
  }
}

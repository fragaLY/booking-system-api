package by.vk.bookingsystem.controller;

import javax.validation.constraints.NotBlank;

import by.vk.bookingsystem.dto.home.HomeDto;
import by.vk.bookingsystem.dto.home.HomeSetDto;
import by.vk.bookingsystem.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller to work with homes
 *
 * @author Vadzim_Kavalkou
 */
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
   * Returns the set of all homes
   *
   * @return {@link ResponseEntity}
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
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
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<HomeDto> getHomeById(
      @NotBlank(message = "The id cannot be blank") @PathVariable final String id) {
    return ResponseEntity.ok(homeService.findHomeById(id));
  }
}

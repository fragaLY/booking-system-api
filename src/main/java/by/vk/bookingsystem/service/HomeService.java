package by.vk.bookingsystem.service;

import by.vk.bookingsystem.dto.home.HomeDto;
import by.vk.bookingsystem.dto.home.HomeSetDto;

/**
 * The service for {@link HomeDto}
 *
 * @author Vadzim_Kavalkou
 */
public interface HomeService {

  /**
   * Finds all homes in the system and returns them.
   *
   * @return {@link HomeSetDto}
   */
  HomeSetDto findAllHomes();

  /**
   * Finds the home by id.
   *
   * @param id - the id of home.
   * @return {@link HomeDto}
   */
  HomeDto findHomeById(String id);
}

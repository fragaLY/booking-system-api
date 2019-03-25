package by.vk.bookingsystem.service;

import by.vk.bookingsystem.dto.price.PriceDto;
import by.vk.bookingsystem.dto.price.PriceSetDto;

/**
 * The service for {@link PriceDto}
 *
 * @author Vadzim_Kavalkou
 */
public interface PriceService {

  /**
   * Finds all prices in the system and returns them.
   *
   * @return {@link PriceSetDto}
   */
  PriceSetDto findAllPrices();

  /**
   * Finds the price by its id.
   *
   * @param id - the id of price
   * @return {@link PriceDto}
   */
  PriceDto findPriceById(String id);
}

package by.vk.bookingsystem.dao;

import java.util.List;

import by.vk.bookingsystem.domain.Price;

/**
 * The data access object layer for {@link Price}
 *
 * @author Vadzim_Kavalkou
 */
public interface PriceDao {

  /**
   * Finds all prices that are in the system and returns it
   *
   * @return the list of {@link Price}
   */
  List<Price> findAll();

  /**
   * Finds the price by its id and returns it
   *
   * @param id - the id of {@link Price}
   * @return {@link Price}
   */
  Price findPriceById(String id);

  /**
   * Finds the price that is present for current number of guests
   *
   * @param guests - the amount of guests
   * @return {@link Price}
   */
  Price findPriceByGuests(int guests);
}

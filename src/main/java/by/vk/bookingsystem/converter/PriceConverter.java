package by.vk.bookingsystem.converter;

import by.vk.bookingsystem.domain.Price;
import by.vk.bookingsystem.dto.price.PriceDto;

/**
 * The converter for {@link Price} and {@link PriceDto}
 *
 * @author Vadzim_Kavalkou
 */
public interface PriceConverter {

  /**
   * Converts entity to data transfer object
   *
   * @param entity - {@link Price}
   * @see by.vk.bookingsystem.converter.impl.PriceConverterImpl
   * @return {@link PriceDto}
   */
  PriceDto convertToDto(Price entity);
}

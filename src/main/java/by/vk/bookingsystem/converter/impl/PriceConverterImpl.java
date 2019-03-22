package by.vk.bookingsystem.converter.impl;

import by.vk.bookingsystem.converter.PriceConverter;
import by.vk.bookingsystem.domain.Price;
import by.vk.bookingsystem.dto.price.PriceDto;
import org.springframework.stereotype.Component;

/**
 * The converter implementation for {@link Price} and {@link PriceDto}
 *
 * @author Vadzim_Kavalkou
 */
@Component
public class PriceConverterImpl implements PriceConverter {

  /**
   * Converts the entity to data transfer object.
   *
   * @param entity - {@link Price}
   * @return {@link PriceDto}
   */
  @Override
  public PriceDto convertToDto(final Price entity) {
    return new PriceDto(entity.getId().toHexString(), entity.getPrice(), entity.getGuests());
  }
}

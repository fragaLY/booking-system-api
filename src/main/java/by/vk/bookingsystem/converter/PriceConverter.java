package by.vk.bookingsystem.converter;

import by.vk.bookingsystem.domain.Price;
import by.vk.bookingsystem.dto.price.PriceDto;

public interface PriceConverter {

  /**
   * Converts entity to data transfer object
   *
   * @param entity - the entity
   * @return the {@link PriceDto}
   */
  PriceDto convertToDto(Price entity);

  /**
   * Converts dto to entity
   *
   * @param dto - the data transfer object
   * @return the {@link Price}
   */
  Price convertToEntity(PriceDto dto);
}

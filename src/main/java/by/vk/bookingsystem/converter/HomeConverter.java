package by.vk.bookingsystem.converter;

import by.vk.bookingsystem.domain.Home;
import by.vk.bookingsystem.dto.home.HomeDto;

public interface HomeConverter {

  /**
   * Converts entity to data transfer object
   *
   * @param entity - the entity
   * @return the {@link HomeDto}
   */
  HomeDto convertToDto(Home entity);

  /**
   * Converts dto to entity
   *
   * @param dto - the data transfer object
   * @return the {@link Home}
   */
  Home convertToEntity(HomeDto dto);
}

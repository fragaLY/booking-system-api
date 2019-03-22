package by.vk.bookingsystem.converter;

import by.vk.bookingsystem.domain.Home;
import by.vk.bookingsystem.dto.home.HomeDto;

/**
 * The converter for {@link Home} and {@link HomeDto}
 *
 * @author Vadzim_Kavalkou
 */
public interface HomeConverter {

  /**
   * Converts entity to data transfer object
   *
   * @param entity - {@link Home}
   * @see by.vk.bookingsystem.converter.impl.HomeConverterImpl
   * @return {@link HomeDto}
   */
  HomeDto convertToDto(Home entity);

  /**
   * Converts dto to entity
   *
   * @param dto - {@link HomeDto}
   * @see by.vk.bookingsystem.converter.impl.HomeConverterImpl
   * @return {@link Home}
   */
  Home convertToEntity(HomeDto dto);
}

package by.vk.bookingsystem.converter.impl;

import by.vk.bookingsystem.converter.HomeConverter;
import by.vk.bookingsystem.domain.Home;
import by.vk.bookingsystem.dto.home.HomeDto;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

/**
 * The converter implementation for {@link Home} and {@link HomeDto}
 *
 * @author Vadzim_Kavalkou
 */
@Component
public class HomeConverterImpl implements HomeConverter {

  /**
   * Converts entity to dto
   *
   * @param entity - {@link Home}
   * @return {@link HomeDto}
   */
  @Override
  public HomeDto convertToDto(final Home entity) {
    return new HomeDto(entity.getId().toHexString(), entity.getName());
  }

  /**
   * Converts dto to entity
   *
   * @param dto - {@link HomeDto}
   * @return {@link Home}
   */
  @Override
  public Home convertToEntity(final HomeDto dto) {
    return new Home(new ObjectId(dto.getHomeId()), dto.getName());
  }
}

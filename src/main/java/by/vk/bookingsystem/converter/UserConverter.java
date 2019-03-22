package by.vk.bookingsystem.converter;

import by.vk.bookingsystem.domain.User;
import by.vk.bookingsystem.dto.user.UserDto;

/**
 * The converter for {@link User} and {@link UserDto}
 *
 * @author Vadzim_Kavalkou
 */
public interface UserConverter {

  /**
   * Converts entity to data transfer object
   *
   * @param entity - {@link User}
   * @see by.vk.bookingsystem.converter.impl.UserConverterImpl
   * @return {@link UserDto}
   */
  UserDto convertToDto(User entity);

  /**
   * Converts dto to entity
   *
   * @param dto - {@link UserDto}
   * @see by.vk.bookingsystem.converter.impl.UserConverterImpl
   * @return {@link User}
   */
  User convertToEntity(UserDto dto);

  /**
   * Enriches the entity with new information from data transfer object
   *
   * @param entity - {@link User}
   * @param dto - {@link UserDto}
   * @see by.vk.bookingsystem.converter.impl.UserConverterImpl
   * @return {@link User}
   */
  User enrichModel(User entity, UserDto dto);
}

package by.vk.bookingsystem.converter;

import by.vk.bookingsystem.domain.User;
import by.vk.bookingsystem.dto.user.UserDto;

public interface UserConverter {

  /**
   * Converts entity to data transfer object
   *
   * @param entity - the entity
   * @return the {@link UserDto}
   */
  UserDto convertToDto(User entity);

  /**
   * Converts dto to entity
   *
   * @param dto - the data transfer object
   * @return the {@link User}
   */
  User convertToEntity(UserDto dto);

  User enrichModel(User user, UserDto dto);
}

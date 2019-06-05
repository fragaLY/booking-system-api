package by.vk.bookingsystem.converter.impl;

import by.vk.bookingsystem.converter.UserConverter;
import by.vk.bookingsystem.domain.User;
import by.vk.bookingsystem.domain.role.Role;
import by.vk.bookingsystem.dto.user.UserDto;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * The converter implementation for {@link User} and {@link UserDto}
 *
 * @author Vadzim_Kavalkou
 */
@Component
public class UserConverterImpl implements UserConverter {

  /**
   * Converts the entity to data transfer object
   *
   * @param user - {@link User}
   * @return {@link UserDto}
   */
  @Override
  public UserDto convertToDto(final User user) {
    final UserDto dto =
        UserDto.newBuilder()
            .setId(user.getId().toHexString())
            .setFirstName(user.getFirstName())
            .setLastName(user.getLastName())
            .setRole(user.getRole().name())
            .setEmail(user.getEmail())
            .setPhone(user.getPhone())
            .setCurrencyCode(user.getCurrencyCode())
            .setCountry(user.getCountry())
            .setCity(user.getCity())
            .setRegistered(user.getRegistered())
            .setPassword(user.getPassword())
            .build();

    dto.add(linkTo(UserConverter.class).slash(user.getId()).withSelfRel());

    return dto;
  }

  /**
   * Converts the data transfer object to entity
   *
   * @param dto - {@link UserDto}
   * @return {@link User}
   */
  @Override
  public User convertToEntity(final UserDto dto) {
    return User.builder()
        .id(dto.getUserId() != null ? new ObjectId(dto.getUserId()) : null)
        .firstName(dto.getFirstName())
        .lastName(dto.getLastName())
        .role(Role.getRole(dto.getRole()))
        .email(dto.getEmail())
        .phone(dto.getPhone())
        .currencyCode(dto.getCurrencyCode())
        .country(dto.getCountry())
        .city(dto.getCity())
        .registered(dto.getRegistered())
        .password(dto.getPassword())
        .build();
  }

  /**
   * Enriches the entity with new information from data transfer object
   *
   * @param entity - {@link User}
   * @param dto - {@link UserDto}
   * @return {@link User}
   */
  @Override
  public User enrichModel(final User entity, final UserDto dto) {
    entity.setFirstName(dto.getFirstName());
    entity.setLastName(dto.getLastName());
    entity.setEmail(dto.getEmail());
    entity.setPhone(dto.getPhone());
    entity.setCurrencyCode(dto.getCurrencyCode());
    entity.setCountry(dto.getCountry());
    entity.setCity(dto.getCity());
    entity.setPassword(dto.getPassword());
    return entity;
  }
}

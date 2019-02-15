package by.vk.bookingsystem.converter.impl;

import by.vk.bookingsystem.converter.UserConverter;
import by.vk.bookingsystem.domain.User;
import by.vk.bookingsystem.domain.role.Role;
import by.vk.bookingsystem.dto.user.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements UserConverter {

  @Override
  public UserDto convertToDto(final User entity) {
    return UserDto.builder()
        .id(entity.getId())
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .role(entity.getRole().toString().toUpperCase())
        .email(entity.getEmail())
        .phone(entity.getPhone())
        .currencyCode(entity.getCurrencyCode())
        .country(entity.getCountry())
        .city(entity.getCity())
        .registered(entity.getRegistered())
        .password(entity.getPassword())
        .build();
  }

  @Override
  public User convertToEntity(final UserDto dto) {
    return User.builder()
        .id(dto.getId())
        .firstName(dto.getFirstName())
        .lastName(dto.getLastName())
        .role(Role.getRole(dto.getRole().toUpperCase()))
        .email(dto.getEmail())
        .phone(dto.getPhone())
        .currencyCode(dto.getCurrencyCode())
        .country(dto.getCountry())
        .city(dto.getCity())
        .registered(dto.getRegistered())
        .password(dto.getPassword())
        .build();
  }
}

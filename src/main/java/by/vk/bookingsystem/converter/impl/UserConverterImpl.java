package by.vk.bookingsystem.converter.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;

import by.vk.bookingsystem.converter.UserConverter;
import by.vk.bookingsystem.domain.User;
import by.vk.bookingsystem.domain.role.Role;
import by.vk.bookingsystem.dto.user.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements UserConverter {

  private static final String EUROPE_MINSK = "Europe/Minsk";

  @Override
  public UserDto convertToDto(final User entity) {
    return new UserDto(); //todo vk: add convertation
  }

  @Override
  public User convertToEntity(final UserDto dto) {
    return User.builder()
        .firstName(dto.getFirstName())
        .lastName(dto.getLastName())
        .role(Role.getRole(dto.getRole()))
        .email(dto.getEmail())
        .phone(dto.getPhone())
        .currencyCode(dto.getCurrencyCode())
        .country(dto.getCountry())
        .city(dto.getCity())
        .registered(LocalDateTime.now(ZoneId.of(EUROPE_MINSK)))
        .password(dto.getPassword())
        .build();
  }

  @Override
  public User enrichModel(final User user, final UserDto dto) {
    user.setFirstName(dto.getFirstName());
    user.setLastName(dto.getLastName());
    user.setEmail(dto.getEmail());
    user.setPhone(dto.getPhone());
    user.setCurrencyCode(dto.getCurrencyCode());
    user.setCountry(dto.getCountry());
    user.setCity(dto.getCity());
    user.setPassword(dto.getPassword());
    return user;
  }
}

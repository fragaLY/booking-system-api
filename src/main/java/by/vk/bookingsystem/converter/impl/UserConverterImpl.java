package by.vk.bookingsystem.converter.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;

import by.vk.bookingsystem.converter.UserConverter;
import by.vk.bookingsystem.dao.UserDao;
import by.vk.bookingsystem.domain.User;
import by.vk.bookingsystem.domain.role.Role;
import by.vk.bookingsystem.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements UserConverter {

  private static final String EUROPE_MINSK = "Europe/Minsk";

  private final UserDao userDao;

  @Autowired
  public UserConverterImpl(final UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public UserDto convertToDto(final User entity) { // todo vk: possible npe
    return UserDto.builder()
        .id(entity.getId().toHexString())
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
    final String id = dto.getId();
    final User user;

    if (id == null) {
      user =
          User.builder()
              .id(null)
              .firstName(dto.getFirstName())
              .lastName(dto.getLastName())
              .role(Role.USER)
              .email(dto.getEmail())
              .phone(dto.getPhone())
              .currencyCode(dto.getCurrencyCode())
              .country(dto.getCountry())
              .city(dto.getCity())
              .registered(LocalDateTime.now(ZoneId.of(EUROPE_MINSK)))
              .password(dto.getPassword())
              .build();
    } else {
        user = userDao.findUserById(id);
        if(user)

    }

    return user;
  }
}

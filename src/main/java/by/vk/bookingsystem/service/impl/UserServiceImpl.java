package by.vk.bookingsystem.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import by.vk.bookingsystem.converter.UserConverter;
import by.vk.bookingsystem.dao.UserDao;
import by.vk.bookingsystem.domain.User;
import by.vk.bookingsystem.dto.user.UserDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@PropertySources(@PropertySource("classpath:i18n/validation_errors.properties"))
public class UserServiceImpl implements UserService {

  private final UserDao userDao;
  private final UserConverter userConverter;
  private final Environment environment;

  @Autowired
  public UserServiceImpl(
      final UserDao userDao, final UserConverter userConverter, final Environment environment) {
    this.userDao = userDao;
    this.userConverter = userConverter;
    this.environment = environment;
  }

  @Override
  public List<UserDto> findAllUsers() {
    return userDao.findAll().stream()
        .filter(Objects::nonNull)
        .map(userConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  public UserDto findUserById(final String id) {
    final User user = userDao.findUserById(id);

    if (user == null) {
      throw new ObjectNotFoundException(
          environment.getProperty(ObjectNotFoundException.class.getName().toLowerCase()));
    }

    return userConverter.convertToDto(user);
  }

  @Override
  public String createUser(final UserDto dto) {
    return userDao.save(userConverter.convertToEntity(dto)).getId().toHexString();
  }

  @Override
  public String updateUser(final UserDto dto, final String id) {
    final User user = userDao.findUserById(id);

    if (user == null) {
      throw new ObjectNotFoundException(
          environment.getProperty(ObjectNotFoundException.class.getName().toLowerCase()));
    }

    return userDao.save(userConverter.enrichModel(user, dto)).getId().toHexString();
  }

  @Override
  public boolean deleteUserById(final String id) {
    if (userDao.findUserById(id) == null) {
      throw new ObjectNotFoundException(ObjectNotFoundException.class.getName().toLowerCase());
    }
    userDao.deleteById(new ObjectId(id));
    return true;
  }
}

package by.vk.bookingsystem.service.impl;

import java.util.Objects;
import java.util.stream.Collectors;

import by.vk.bookingsystem.converter.UserConverter;
import by.vk.bookingsystem.dao.UserDao;
import by.vk.bookingsystem.domain.User;
import by.vk.bookingsystem.dto.user.UserDto;
import by.vk.bookingsystem.dto.user.UserSetDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.exception.user.EmailAlreadyRegisteredException;
import by.vk.bookingsystem.exception.user.PhoneAlreadyRegisteredException;
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

  private static final String SPLITTER = ".";

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
  public UserSetDto findAllUsers() {
    return new UserSetDto(
        userDao.findAll().stream()
            .filter(Objects::nonNull)
            .map(userConverter::convertToDto)
            .collect(Collectors.toSet()));
  }

  @Override
  public UserDto findUserById(final String id) {
    final User user = userDao.findUserById(id);

    if (user == null) {
      throw new ObjectNotFoundException(
          environment.getProperty(
              User.class.getSimpleName().toLowerCase()
                  + SPLITTER
                  + ObjectNotFoundException.class.getSimpleName().toLowerCase()));
    }

    return userConverter.convertToDto(user);
  }

  @Override
  public String createUser(final UserDto dto) {
    final User userWithSameEmail = userDao.findUserByEmail(dto.getEmail());
    final User userWithSamePhone = userDao.findUserByPhone(dto.getPhone());

    if (userWithSameEmail != null) {
      throw new EmailAlreadyRegisteredException(
          environment.getProperty(
              User.class.getSimpleName().toLowerCase()
                  + SPLITTER
                  + EmailAlreadyRegisteredException.class.getSimpleName().toLowerCase()));
    }

    if (userWithSamePhone != null) {
      throw new PhoneAlreadyRegisteredException(
          environment.getProperty(
              User.class.getSimpleName().toLowerCase()
                  + SPLITTER
                  + PhoneAlreadyRegisteredException.class.getSimpleName().toLowerCase()));
    }

    return userDao.save(userConverter.convertToEntity(dto)).getId().toHexString();
  }

  @Override
  public String updateUser(final UserDto dto, final String id) {
    final User user = userDao.findUserById(id);

    if (user == null) {
      throw new ObjectNotFoundException(
          environment.getProperty(
              User.class.getSimpleName().toLowerCase()
                  + SPLITTER
                  + ObjectNotFoundException.class.getSimpleName().toLowerCase()));
    }

    return userDao.save(userConverter.enrichModel(user, dto)).getId().toHexString();
  }

  @Override
  public boolean deleteUserById(final String id) {
    if (userDao.findUserById(id) == null) {
      throw new ObjectNotFoundException(
          environment.getProperty(
              User.class.getSimpleName().toLowerCase()
                  + SPLITTER
                  + ObjectNotFoundException.class.getSimpleName().toLowerCase()));
    }
    userDao.deleteById(new ObjectId(id));
    return true;
  }
}

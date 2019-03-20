package by.vk.bookingsystem.service.impl;

import java.util.Objects;
import java.util.stream.Collectors;

import by.vk.bookingsystem.converter.UserConverter;
import by.vk.bookingsystem.dao.UserMongoDao;
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
public class UserServiceMongoImpl implements UserService {

  private static final String USER_NOT_FOUND = "user.not.found";
  private static final String EMAIL_ALREADY_REGISTERED = "user.email.already.registered";
  private static final String PHONE_ALREADY_REGISTERED = "user.phone.already.registered";

  private final UserMongoDao userDao;
  private final UserConverter userConverter;
  private final Environment environment;

  @Autowired
  public UserServiceMongoImpl(
      final UserMongoDao userDao,
      final UserConverter userConverter,
      final Environment environment) {
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
      throw new ObjectNotFoundException(environment.getProperty(USER_NOT_FOUND));
    }

    return userConverter.convertToDto(user);
  }

  @Override
  public String createUser(final UserDto dto) {
    final User userWithSameEmail = userDao.findUserByEmail(dto.getEmail());
    final User userWithSamePhone = userDao.findUserByPhone(dto.getPhone());

    if (userWithSameEmail != null) {
      throw new EmailAlreadyRegisteredException(environment.getProperty(EMAIL_ALREADY_REGISTERED));
    }

    if (userWithSamePhone != null) {
      throw new PhoneAlreadyRegisteredException(environment.getProperty(PHONE_ALREADY_REGISTERED));
    }

    return userDao.save(userConverter.convertToEntity(dto)).getId().toHexString();
  }

  @Override
  public String updateUser(final UserDto dto, final String id) {
    final User user = userDao.findUserById(id);

    if (user == null) {
      throw new ObjectNotFoundException(environment.getProperty(USER_NOT_FOUND));
    }

    return userDao.save(userConverter.enrichModel(user, dto)).getId().toHexString();
  }

  @Override
  public boolean deleteUserById(final String id) {
    if (userDao.findUserById(id) == null) {
      throw new ObjectNotFoundException(environment.getProperty(USER_NOT_FOUND));
    }
    userDao.deleteById(new ObjectId(id));
    return true;
  }
}

package by.vk.bookingsystem.service.impl;

import java.util.Objects;
import java.util.stream.Collectors;

import by.vk.bookingsystem.converter.UserConverter;
import by.vk.bookingsystem.dao.UserDao;
import by.vk.bookingsystem.domain.User;
import by.vk.bookingsystem.dto.user.UserDto;
import by.vk.bookingsystem.dto.user.UserSetDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.UserService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * The service implementation for {@link UserDto}
 *
 * @author Vadzim_Kavalkou
 */
@Service
@PropertySources(@PropertySource("classpath:i18n/validation_errors.properties"))
public class UserServiceImpl implements UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

  private static final String USER_NOT_FOUND = "user.not.found";
  private static final String EMAIL_ALREADY_REGISTERED = "user.email.already.registered";
  private static final String PHONE_ALREADY_REGISTERED = "user.phone.already.registered";

  private final UserDao userDao;
  private final UserConverter userConverter;
  private final Environment environment;

  /**
   * The constructor with parameters.
   *
   * @param userDao - {@link UserDao}
   * @param userConverter - {@link UserConverter}
   * @param environment - {@link Environment}
   */
  @Autowired
  public UserServiceImpl(
      final UserDao userDao, final UserConverter userConverter, final Environment environment) {
    this.userDao = userDao;
    this.userConverter = userConverter;
    this.environment = environment;
  }

  /**
   * Finds all users in the system and returns them.
   *
   * @return {@link UserSetDto}
   */
  @Override
  public UserSetDto findAllUsers() {
    return new UserSetDto(
        userDao.findAll().stream()
            .filter(Objects::nonNull)
            .map(userConverter::convertToDto)
            .collect(Collectors.toSet()));
  }

  /**
   * Finds the user by its id.
   *
   * @param id - the id of price
   * @return {@link UserDto}
   */
  @Override
  public UserDto findUserById(final String id) {
    final User user = userDao.findUserById(id);

    if (user == null) {
      LOGGER.error("User with id {0} was not found.", id);
      throw new ObjectNotFoundException(environment.getProperty(USER_NOT_FOUND));
    }

    return userConverter.convertToDto(user);
  }

  /**
   * Creates the user and returns its id
   *
   * @param user - {@link UserDto}
   * @return {@link String}
   */
  @Override
  public String createUser(final UserDto user) {
    final User userWithSameEmail = userDao.findUserByEmail(user.getEmail());

    if (userWithSameEmail != null) {
      LOGGER.error("The user {0} uses already existed email by user {1}", user, userWithSameEmail);
      throw new IllegalArgumentException(environment.getProperty(EMAIL_ALREADY_REGISTERED));
    }

    final User userWithSamePhone = userDao.findUserByPhone(user.getPhone());

    if (userWithSamePhone != null) {
      LOGGER.error("The user {0} uses already existed phone by user {1}", user, userWithSamePhone);
      throw new IllegalArgumentException(environment.getProperty(PHONE_ALREADY_REGISTERED));
    }

    return userDao.save(userConverter.convertToEntity(user)).getId().toHexString();
  }

  /**
   * Enriches the user with new information from data transfer object and updates it.
   *
   * @param dto - {@link UserDto}
   * @param id - the id of user.
   */
  @Override
  public void updateUser(final UserDto dto, final String id) {
    final User user = userDao.findUserById(id);

    if (user == null) {
      LOGGER.error("User with id {0} was not found.", id);
      throw new ObjectNotFoundException(environment.getProperty(USER_NOT_FOUND));
    }

    userDao.save(userConverter.enrichModel(user, dto)).getId().toHexString();
  }

  /**
   * Deletes user by its id.
   *
   * @param id - the id of user
   */
  @Override
  public void deleteUserById(final String id) {
    if (userDao.findUserById(id) == null) {
      LOGGER.error("User with id {0} was not found.", id);
      throw new ObjectNotFoundException(environment.getProperty(USER_NOT_FOUND));
    }
    userDao.deleteById(new ObjectId(id));
  }
}

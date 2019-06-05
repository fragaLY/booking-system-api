package by.vk.bookingsystem.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import by.vk.bookingsystem.controller.UserController;
import by.vk.bookingsystem.converter.UserConverter;
import by.vk.bookingsystem.dao.UserDao;
import by.vk.bookingsystem.domain.User;
import by.vk.bookingsystem.dto.user.UserDto;
import by.vk.bookingsystem.dto.user.UserSetDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.UserService;
import by.vk.bookingsystem.validator.order.impl.OrderValidatorImpl;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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
  private static final String REGISTERED_DATE_COULD_NOT_BE_CHANGED = "user.registered.changed";

  private static final String USER_NOT_FOUND_LOG = "User with id {} was not found.";
  private static final String EMAIL_ALREADY_REGISTERED_LOG =
      "The user {} uses already existed email";
  private static final String PHONE_ALREADY_REGISTERED_LOG =
      "The user {} uses already existed phone";

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
   * Finds users between selected dates in the system and returns it
   *
   * @param from {@link LocalDate}
   * @param to {@link LocalDate}
   * @return {@link UserSetDto}
   */
  @Cacheable(value = "users", key = "{#from, #to}")
  @Override
  public UserSetDto findAllUsersBetweenDates(LocalDate from, LocalDate to) {

    if (from.isAfter(to)) {
      LOGGER.warn(OrderValidatorImpl.INVALID_DATES_LOG, from, to);
      throw new IllegalArgumentException(environment.getProperty(OrderValidatorImpl.INVALID_DATES));
    }

    final Set<UserDto> userSet =
        userDao.findAllUsersBetweenDates(from, to).stream()
            .filter(Objects::nonNull)
            .map(userConverter::convertToDto)
            .collect(Collectors.toSet());

    final UserSetDto userSetDto = new UserSetDto(userSet);

    userSetDto.add(new Link(linkTo(UserController.class).toString()).withSelfRel());

    return userSetDto;
  }

  /**
   * Finds the user by its id.
   *
   * <p>If entity with current id is not in the system throws the {@link ObjectNotFoundException}
   *
   * @param id - the id of price. Not null.
   * @return {@link UserDto}
   * @throws ObjectNotFoundException the exception of absence any instance
   */
  @Cacheable(value = "user", key = "#id")
  @Override
  public UserDto findUserById(final String id) {

    if (!userDao.existsById(id)) {
      LOGGER.warn(USER_NOT_FOUND_LOG, id);
      throw new ObjectNotFoundException(environment.getProperty(USER_NOT_FOUND));
    }

    return userConverter.convertToDto(userDao.findUserById(id));
  }

  /**
   * Creates the user and returns its id.
   *
   * <p>If email or phone are already in use throws {@link IllegalArgumentException}
   *
   * @param user - {@link UserDto}
   * @return {@link String}
   */
  @CacheEvict(value = "users", allEntries = true)
  @Override
  public String createUser(final UserDto user) {

    if (userDao.existsByEmail(user.getEmail())) {
      LOGGER.warn(EMAIL_ALREADY_REGISTERED_LOG, user);
      throw new IllegalArgumentException(environment.getProperty(EMAIL_ALREADY_REGISTERED));
    }

    if (userDao.existsByPhone(user.getPhone())) {
      LOGGER.warn(PHONE_ALREADY_REGISTERED_LOG, user);
      throw new IllegalArgumentException(environment.getProperty(PHONE_ALREADY_REGISTERED));
    }

    return userDao.save(userConverter.convertToEntity(user)).getId().toHexString();
  }

  /**
   * Enriches the user with new information from data transfer object and updates it.
   *
   * <p>If entity with current id is not in the system throws the {@link ObjectNotFoundException} *
   *
   * <p>If new email or new phone are already in use throws {@link IllegalArgumentException}
   *
   * @param user - {@link UserDto}
   * @param id - the id of user.
   */
  @Caching(
      evict = {
        @CacheEvict(value = "users", allEntries = true),
        @CacheEvict(value = "user", key = "#id")
      })
  @Override
  public void updateUser(final UserDto user, final String id) {

    if (!userDao.existsById(id)) {
      LOGGER.warn(USER_NOT_FOUND_LOG, id);
      throw new ObjectNotFoundException(environment.getProperty(USER_NOT_FOUND));
    }

    final User userToUpdate = userDao.findUserById(id);

    final String newEmail = user.getEmail();
    final String oldEmail = userToUpdate.getEmail();

    if (!oldEmail.equalsIgnoreCase(newEmail) && userDao.existsByEmail(newEmail)) {
      LOGGER.warn(EMAIL_ALREADY_REGISTERED_LOG, user);
      throw new IllegalArgumentException(environment.getProperty(EMAIL_ALREADY_REGISTERED));
    }

    final String newPhone = user.getPhone();
    final String oldPhone = userToUpdate.getPhone();

    if (!oldPhone.equalsIgnoreCase(newPhone) && userDao.existsByPhone(newPhone)) {
      LOGGER.warn(PHONE_ALREADY_REGISTERED_LOG, user);
      throw new IllegalArgumentException(environment.getProperty(PHONE_ALREADY_REGISTERED));
    }

    final LocalDateTime oldRegistered = userToUpdate.getRegistered();
    final LocalDateTime newRegistered = user.getRegistered();

    if (!oldRegistered.equals(newRegistered)) {
      LOGGER.warn(environment.getProperty(REGISTERED_DATE_COULD_NOT_BE_CHANGED), user);
      throw new IllegalArgumentException(
          environment.getProperty(REGISTERED_DATE_COULD_NOT_BE_CHANGED));
    }

    userDao.save(userConverter.enrichModel(userToUpdate, user));
  }

  /**
   * Deletes user by its id.
   *
   * <p>If entity with current id is not in the system throws the {@link ObjectNotFoundException}
   *
   * @param id - the id of user
   */
  @Caching(
      evict = {
        @CacheEvict(value = "users", allEntries = true),
        @CacheEvict(value = "user", key = "#id")
      })
  @Override
  public void deleteUserById(final String id) {

    if (!userDao.existsById(id)) {
      LOGGER.warn(USER_NOT_FOUND_LOG, id);
      throw new ObjectNotFoundException(environment.getProperty(USER_NOT_FOUND));
    }

    userDao.deleteById(new ObjectId(id));
  }
}

package by.vk.bookingsystem.service;

import java.time.LocalDate;

import by.vk.bookingsystem.dto.user.UserDto;
import by.vk.bookingsystem.dto.user.UserSetDto;

/**
 * The service for {@link UserDto}
 *
 * @author Vadzim_Kavalkou
 */
public interface UserService {

  /**
   * Finds all users in the system and returns them *
   *
   * @param from {@link LocalDate}
   * @param to {@link LocalDate}
   * @return {@link UserSetDto}
   */
  UserSetDto findAllUsersBetweenDates(LocalDate from, LocalDate to);

  /**
   * Finds the user by its id.
   *
   * @param id - the id of price
   * @return {@link UserDto}
   */
  UserDto findUserById(String id);

  /**
   * Creates the user and returns its id
   *
   * @param dto - {@link UserDto}
   * @return {@link String}
   */
  String createUser(UserDto dto);

  /**
   * Enriches the user with new information from data transfer object and updates it.
   *
   * @param dto - {@link UserDto}
   * @param id - the id of user.
   */
  void updateUser(UserDto dto, String id);

  /**
   * Deletes user by its id.
   *
   * @param id - the id of user
   */
  void deleteUserById(String id);
}

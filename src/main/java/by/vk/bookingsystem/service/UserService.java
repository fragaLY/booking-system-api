package by.vk.bookingsystem.service;

import by.vk.bookingsystem.dto.user.UserDto;
import by.vk.bookingsystem.dto.user.UserSetDto;

public interface UserService {

  UserSetDto findAllUsers();

  UserDto findUserById(String id);

  String createUser(UserDto dto);

  String updateUser(UserDto dto, String id);

  boolean deleteUserById(String id);
}

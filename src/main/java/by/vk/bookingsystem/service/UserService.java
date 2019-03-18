package by.vk.bookingsystem.service;

import java.util.List;

import by.vk.bookingsystem.dto.user.UserDto;

public interface UserService {

  List<UserDto> findAllUsers();

  UserDto findUserById(String id);

  String createUser(UserDto dto);

  String updateUser(UserDto dto, String id);

  boolean deleteUserById(String id);
}

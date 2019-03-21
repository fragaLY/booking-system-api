package by.vk.bookingsystem.service;

import by.vk.bookingsystem.dto.user.UserDto;
import by.vk.bookingsystem.dto.user.UserSetDto;

public interface UserService {

  UserSetDto findAllUsers();

  UserDto findUserById(String id);

  String createUser(UserDto dto);

    void updateUser(UserDto dto, String id);

    void deleteUserById(String id);
}

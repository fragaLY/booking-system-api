package by.vk.bookingsystem.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import by.vk.bookingsystem.converter.UserConverter;
import by.vk.bookingsystem.dao.UserDao;
import by.vk.bookingsystem.dto.user.UserDto;
import by.vk.bookingsystem.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserDao userDao;
  private final UserConverter userConverter;

  @Autowired
  public UserServiceImpl(final UserDao userDao, final UserConverter userConverter) {
    this.userDao = userDao;
    this.userConverter = userConverter;
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
    return userConverter.convertToDto(userDao.findUserById(id));
  }

  @Override
  public String saveOrUpdateUser(final UserDto userDto, final String id) {
    userDto.setId(id);
    return userDao.save(userConverter.convertToEntity(userDto)).getId().toHexString();
  }

  @Override
  public boolean deleteUserById(String id) {
    userDao.deleteById(new ObjectId(id));
    return true;
  }
}

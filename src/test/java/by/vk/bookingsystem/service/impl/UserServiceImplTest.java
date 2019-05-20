package by.vk.bookingsystem.service.impl;

import static org.junit.Assert.assertEquals;

import by.vk.bookingsystem.converter.UserConverter;
import by.vk.bookingsystem.dao.UserDao;
import by.vk.bookingsystem.domain.User;
import by.vk.bookingsystem.dto.user.UserDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.UserService;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

  private static final String USER1_ID_VALUE = "5c8fba4cc077d3614023f871";
  private static final String USER1_EMAIL = "email1@mail.com";
  private static final String USER1_PHONE = "3752911111111";

  @MockBean private UserDao userDao;
  @MockBean private UserConverter userConverter;
  @MockBean private Environment environment;

  private User user1;
  private UserDto userDto1;

  private UserService userService;

  @Before
  public void setUp() {

    user1 =
        User.builder()
            .id(new ObjectId(USER1_ID_VALUE))
            .email(USER1_EMAIL)
            .phone(USER1_PHONE)
            .build();

    userDto1 =
        UserDto.newBuilder()
            .setId(USER1_ID_VALUE)
            .setEmail(USER1_EMAIL)
            .setPhone(USER1_PHONE)
            .build();

    userService = new UserServiceImpl(userDao, userConverter, environment);
  }

  @Test(expected = ObjectNotFoundException.class)
  public void findUserById_whenUserNotExists() {

    // given
    Mockito.when(userDao.existsById(USER1_ID_VALUE)).thenReturn(false);

    // when
    userService.findUserById(USER1_ID_VALUE);
  }

  @Test
  public void findUserById_whenUserExists() {

    // given
    Mockito.when(userDao.existsById(USER1_ID_VALUE)).thenReturn(true);
    Mockito.when(userDao.findUserById(USER1_ID_VALUE)).thenReturn(user1);
    Mockito.when(userConverter.convertToDto(user1)).thenReturn(userDto1);

    final UserDto expectedResult =
        UserDto.newBuilder()
            .setId(USER1_ID_VALUE)
            .setEmail(USER1_EMAIL)
            .setPhone(USER1_PHONE)
            .build();

    // when
    final UserDto actualResult = userService.findUserById(USER1_ID_VALUE);

    // then
    assertEquals(expectedResult, actualResult);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createUser_whenEmailAlreadyRegistered() {

    // given
    Mockito.when(userDao.existsByEmail(USER1_EMAIL)).thenReturn(true);

    // when
    userService.createUser(userDto1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createUser_whenPhoneAlreadyRegistered() {

    // given
    Mockito.when(userDao.existsByPhone(USER1_PHONE)).thenReturn(true);

    // when
    userService.createUser(userDto1);
  }

  @Test
  public void createUser_positiveCase() {

    // given
    Mockito.when(userDao.existsByEmail(USER1_EMAIL)).thenReturn(false);
    Mockito.when(userDao.existsByPhone(USER1_PHONE)).thenReturn(false);
    Mockito.when(userConverter.convertToEntity(userDto1)).thenReturn(user1);
    Mockito.when(userDao.save(user1)).thenReturn(user1);

    final String expectedResult = "5c8fba4cc077d3614023f871";

    // when
    final String actualResult = userService.createUser(userDto1);

    // then
    assertEquals(expectedResult, actualResult);
  }

  @Test(expected = ObjectNotFoundException.class)
  public void updateUser_whenUserNotExists() {

    // given
    Mockito.when(userDao.existsById(USER1_ID_VALUE)).thenReturn(false);

    // when
    userService.updateUser(userDto1, USER1_ID_VALUE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void updateUser_whenDifferentEmailsAndNewEmailAlreadyRegistered() {

    // given
    final String newEmail = "new@email.com";

    Mockito.when(userDao.existsById(USER1_ID_VALUE)).thenReturn(true);
    Mockito.when(userDao.findUserById(USER1_ID_VALUE)).thenReturn(user1);
    Mockito.when(userDao.existsByEmail(newEmail)).thenReturn(true);

    // when
    userService.updateUser(UserDto.newBuilder().setEmail(newEmail).build(), USER1_ID_VALUE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void updateUser_whenDifferentPhonesAndNewPhoneAlreadyRegistered() {

    // given
    final String newPhone = "+333333333";
    Mockito.when(userDao.existsById(USER1_ID_VALUE)).thenReturn(true);
    Mockito.when(userDao.findUserById(USER1_ID_VALUE)).thenReturn(user1);
    Mockito.when(userDao.existsByPhone(newPhone)).thenReturn(true);

    // when
    userService.updateUser(UserDto.newBuilder().setPhone(newPhone).build(), USER1_ID_VALUE);
  }

  @Test
  public void updateUser_whenPhoneAlreadyRegistered() {

    // given
    final String newEmail = "new@email.com";
    final String newPhone = "+333333333";
    final UserDto dto = UserDto.newBuilder().setPhone(newPhone).setEmail(newEmail).build();
    final User updatedUser =
        User.builder().id(new ObjectId(USER1_ID_VALUE)).email(newEmail).phone(newPhone).build();

    Mockito.when(userDao.existsById(USER1_ID_VALUE)).thenReturn(true);
    Mockito.when(userDao.findUserById(USER1_ID_VALUE)).thenReturn(user1);
    Mockito.when(userConverter.enrichModel(user1, dto)).thenReturn(updatedUser);
    // when
    userService.updateUser(dto, USER1_ID_VALUE);

    // then
    Mockito.verify(userDao, Mockito.atLeastOnce()).save(updatedUser);
  }

  @Test(expected = ObjectNotFoundException.class)
  public void deleteUserById_whenUserNotExists() {

    // given
    Mockito.when(userDao.existsById(USER1_ID_VALUE)).thenReturn(false);

    // when
    userService.deleteUserById(USER1_ID_VALUE);
  }

  @Test
  public void deleteUserById_positiveCase() {

    // given
    Mockito.when(userDao.existsById(USER1_ID_VALUE)).thenReturn(true);

    // when
    userService.deleteUserById(USER1_ID_VALUE);

    // then
    Mockito.verify(userDao, Mockito.atLeastOnce()).deleteById(new ObjectId(USER1_ID_VALUE));
  }
}

package by.vk.bookingsystem.converter.impl;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import by.vk.bookingsystem.converter.UserConverter;
import by.vk.bookingsystem.domain.User;
import by.vk.bookingsystem.domain.role.Role;
import by.vk.bookingsystem.dto.user.UserDto;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserConverterImplTest {

  private User entity;
  private UserDto dto;

  private UserConverter userConverter;

  @Before
  public void setUp() {

    entity =
        User.builder()
            .id(new ObjectId("5c8fba4cc077d3614023f871"))
            .email("email@email.com")
            .phone("+375292444444")
            .city("Mogilev")
            .country("BY")
            .currencyCode("EUR")
            .firstName("Vadzim")
            .lastName("Kavalkou")
            .password("secret_password_believe_me_my_friend")
            .registered(LocalDateTime.of(1989, 8, 14, 9, 30, 0, 0))
            .role(Role.DEVELOPER)
            .build();

    dto =
        UserDto.newBuilder()
            .setId("5c8fba4cc077d3614023f871")
            .setEmail("email@email.com")
            .setPhone("+375292444444")
            .setCity("Mogilev")
            .setCountry("BY")
            .setCurrencyCode("EUR")
            .setFirstName("Vadzim")
            .setLastName("Kavalkou")
            .setPassword("secret_password_believe_me_my_friend")
            .setRegistered(LocalDateTime.of(1989, 8, 14, 9, 30, 0, 0))
            .setRole(Role.DEVELOPER.name())
            .build();

    userConverter = new UserConverterImpl();
  }

  @Test
  public void convertToDto() {

    // when
    final UserDto actualResult = userConverter.convertToDto(entity);

    // then
    assertEquals(dto, actualResult);
  }

  @Test
  public void convertToEntity() {

    // when
    final User actualResult = userConverter.convertToEntity(dto);

    // then
    assertEquals(entity, actualResult);
  }

  @Test
  public void enrichModel() {

    // given
    dto =
        UserDto.newBuilder()
            .setId("5c8fba4cc077d3614023f872")
            .setEmail("new@email.com")
            .setPhone("+375292333333")
            .setCity("Moscow")
            .setCountry("RUS")
            .setCurrencyCode("RUB")
            .setFirstName("Vadim")
            .setLastName("Kovalkov")
            .setPassword("easy_password")
            .setRegistered(LocalDateTime.now())
            .setRole(Role.MANAGER.name())
            .build();

    final User expectedResult =
        User.builder()
            .id(new ObjectId("5c8fba4cc077d3614023f871"))
            .email("new@email.com")
            .phone("+375292333333")
            .city("Moscow")
            .country("RUS")
            .currencyCode("RUB")
            .firstName("Vadim")
            .lastName("Kovalkov")
            .password("easy_password")
            .registered(LocalDateTime.of(1989, 8, 14, 9, 30, 0, 0))
            .role(Role.DEVELOPER)
            .build();

    // when
    final User actualResult = userConverter.enrichModel(entity, dto);

    // then
    assertEquals(expectedResult, actualResult);
  }
}

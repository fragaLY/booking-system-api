package by.vk.bookingsystem.converter.impl;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import by.vk.bookingsystem.converter.HomeConverter;
import by.vk.bookingsystem.converter.OrderConverter;
import by.vk.bookingsystem.converter.UserConverter;
import by.vk.bookingsystem.domain.Home;
import by.vk.bookingsystem.domain.Order;
import by.vk.bookingsystem.domain.User;
import by.vk.bookingsystem.dto.home.HomeDto;
import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.dto.user.UserDto;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class OrderConverterImplTest {

  @MockBean private HomeConverter homeConverter;
  @MockBean private UserConverter userConverter;

  private OrderConverter orderConverter;

  private Home orderHome;
  private HomeDto orderHomeDto;
  private Set<Home> orderHomes;
  private Set<HomeDto> orderDtoHomes;

  private User owner;
  private UserDto ownerDto;

  @Before
  public void setUp() {
    orderHome = new Home(new ObjectId("5c8fba4cc077d3614023f872"), "HOME");
    orderHomeDto = new HomeDto();
    orderHomes = new HashSet<>(1);
    orderHomes.add(orderHome);

    orderDtoHomes = new HashSet<>(1);
    orderDtoHomes.add(orderHomeDto);

    owner = User.builder().build();
    ownerDto = new UserDto();

    orderConverter = new OrderConverterImpl(homeConverter, userConverter);
  }

  @Test
  public void convertToDto() {

    // given
    Mockito.when(homeConverter.convertToDto(orderHome)).thenReturn(orderHomeDto);
    Mockito.when(userConverter.convertToDto(owner)).thenReturn(ownerDto);

    final Order entity =
        Order.builder()
            .id(new ObjectId("5c8fba4cc077d3614023f871"))
            .confirmed(true)
            .cost(BigDecimal.TEN)
            .guests(1)
            .homes(orderHomes)
            .owner(owner)
            .from(LocalDate.of(2019, 3, 30))
            .to(LocalDate.of(2019, 3, 31))
            .build();

    final OrderDto expectedResult =
        OrderDto.newBuilder()
            .setId("5c8fba4cc077d3614023f871")
            .setFrom(LocalDate.of(2019, 3, 30))
            .setTo(LocalDate.of(2019, 3, 31))
            .setGuests(1)
            .setConfirmed(true)
            .setCost(BigDecimal.TEN)
            .setHomes(orderDtoHomes)
            .setOwner(ownerDto)
            .build();

    // when
    final OrderDto actualResult = orderConverter.convertToDto(entity);

    // then
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void convertToEntity() {

    // given
    Mockito.when(homeConverter.convertToEntity(orderHomeDto)).thenReturn(orderHome);
    Mockito.when(userConverter.convertToEntity(ownerDto)).thenReturn(owner);

    final OrderDto dto =
        OrderDto.newBuilder()
            .setId("5c8fba4cc077d3614023f871")
            .setFrom(LocalDate.of(2019, 3, 30))
            .setTo(LocalDate.of(2019, 3, 31))
            .setGuests(1)
            .setConfirmed(true)
            .setCost(BigDecimal.TEN)
            .setHomes(orderDtoHomes)
            .setOwner(ownerDto)
            .build();

    final Order expectedResult =
        Order.builder()
            .id(new ObjectId("5c8fba4cc077d3614023f871"))
            .confirmed(true)
            .cost(BigDecimal.TEN)
            .guests(1)
            .homes(orderHomes)
            .owner(owner)
            .from(LocalDate.of(2019, 3, 30))
            .to(LocalDate.of(2019, 3, 31))
            .build();

    // when
    final Order actualResult = orderConverter.convertToEntity(dto);

    // then
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void enrichModel() {

    // given

    Mockito.when(homeConverter.convertToEntity(orderHomeDto)).thenReturn(orderHome);
    Mockito.when(userConverter.convertToEntity(ownerDto)).thenReturn(owner);

    final Order order =
        Order.builder()
            .id(new ObjectId("5c8fba4cc077d3614023f871"))
            .confirmed(true)
            .cost(BigDecimal.TEN)
            .guests(1)
            .homes(orderHomes)
            .owner(owner)
            .from(LocalDate.of(2019, 3, 30))
            .to(LocalDate.of(2019, 3, 31))
            .build();

    orderDtoHomes.add(new HomeDto("5c8fba4cc077d3614023f873", "NEW_HOME"));
    final UserDto newOwner = new UserDto();

    final OrderDto dto =
        OrderDto.newBuilder()
            .setId("5c8fba4cc077d3614023f872")
            .setFrom(LocalDate.of(2019, 3, 31))
            .setTo(LocalDate.of(2019, 4, 1))
            .setGuests(2)
            .setConfirmed(false)
            .setCost(BigDecimal.ONE)
            .setHomes(orderDtoHomes)
            .setOwner(newOwner)
            .build();

    final Set<Home> newHomes = new HashSet<>(2);
    newHomes.add(new Home(new ObjectId("5c8fba4cc077d3614023f873"), "NEW_HOME"));
    newHomes.add(new Home(new ObjectId("5c8fba4cc077d3614023f872"), "HOME"));

    final Order expectedResult =
        Order.builder()
            .id(new ObjectId("5c8fba4cc077d3614023f871"))
            .confirmed(false)
            .cost(BigDecimal.ONE)
            .guests(2)
            .homes(newHomes)
            .owner(owner)
            .from(LocalDate.of(2019, 3, 31))
            .to(LocalDate.of(2019, 4, 1))
            .build();

    // when
    final Order actualResult = orderConverter.enrichModel(order, dto);

    // then
    assertEquals(expectedResult, actualResult);
  }
}

package by.vk.bookingsystem.validator.order.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import by.vk.bookingsystem.dao.HomeDao;
import by.vk.bookingsystem.dao.OrderDao;
import by.vk.bookingsystem.dao.UserDao;
import by.vk.bookingsystem.domain.Home;
import by.vk.bookingsystem.domain.User;
import by.vk.bookingsystem.dto.home.HomeDto;
import by.vk.bookingsystem.dto.user.UserDto;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class OrderValidatorImplTest {

  private static final String USER_ID = "5c8fba4cc077d3614023f871";
  private static final String HOME1_ID = "5c8fba4cc077d3614023f872";
  private static final String HOME2_ID = "5c8fba4cc077d3614023f873";

  @MockBean private OrderDao orderDao;
  @MockBean private UserDao userDao;
  @MockBean private HomeDao homeDao;
  @MockBean private Environment environment;

  private OrderValidatorImpl orderValidator;

  @Before
  public void setUp() {
    orderValidator = new OrderValidatorImpl(userDao, homeDao, orderDao, environment);
  }

  @Test(expected = IllegalArgumentException.class)
  public void validateOwner_whenOwnerNotFound() {

    // given
    final UserDto dto = UserDto.newBuilder().setId(USER_ID).build();
    Mockito.when(userDao.findUserById(USER_ID)).thenReturn(null);

    // when
    orderValidator.validateOwner(dto);
  }

  @Test
  public void validateOwner_whenOwnerFound() {

    // given
    final UserDto dto = UserDto.newBuilder().setId(USER_ID).build();
    final User user = User.builder().id(new ObjectId(USER_ID)).build();
    Mockito.when(userDao.findUserById(USER_ID)).thenReturn(user);

    // when
    orderValidator.validateOwner(dto);
  }

  @Test(expected = NullPointerException.class)
  public void validateOwner_whenOwnerNull() {
    orderValidator.validateOwner(null);
  }

  @Test(expected = NullPointerException.class)
  public void validateHomes_whenHomesNull() {
    orderValidator.validateHomes(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void validateHomes_whenHomesNotNullAndTheyAreNotInSystem() {

    // given
    final Set<HomeDto> homeDtoSet = new HashSet<>(2);
    homeDtoSet.add(new HomeDto(HOME1_ID, "HOME1"));
    homeDtoSet.add(new HomeDto(HOME2_ID, "HOME2"));

    final Set<ObjectId> homeIds = new HashSet<>(2);
    homeIds.add(new ObjectId(HOME1_ID));
    homeIds.add(new ObjectId(HOME2_ID));

    Mockito.when(homeDao.findAllById(homeIds)).thenReturn(new ArrayList<>());

    // when
    orderValidator.validateHomes(homeDtoSet);
  }

  @Test
  public void validateHomes_whenHomesNotNullAndTheyAreInSystem() {

    // given
    final Set<HomeDto> homeDtoSet = new HashSet<>(2);
    homeDtoSet.add(new HomeDto(HOME1_ID, "HOME1"));
    homeDtoSet.add(new HomeDto(HOME2_ID, "HOME2"));

    final Set<ObjectId> homeIds = new HashSet<>(2);
    homeIds.add(new ObjectId(HOME1_ID));
    homeIds.add(new ObjectId(HOME2_ID));

    final List<Home> homes = new ArrayList<>(2);
    homes.add(new Home(new ObjectId(HOME1_ID), "HOME1"));
    homes.add(new Home(new ObjectId(HOME2_ID), "HOME2"));

    Mockito.when(homeDao.findAllById(homeIds)).thenReturn(homes);

    // when
    orderValidator.validateHomes(homeDtoSet);
  }

  @Ignore
  @Test
  public void validateOrderDates() {}
}

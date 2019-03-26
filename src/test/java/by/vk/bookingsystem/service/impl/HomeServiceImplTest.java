package by.vk.bookingsystem.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import by.vk.bookingsystem.converter.HomeConverter;
import by.vk.bookingsystem.dao.HomeDao;
import by.vk.bookingsystem.domain.Home;
import by.vk.bookingsystem.dto.home.HomeDto;
import by.vk.bookingsystem.dto.home.HomeSetDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.HomeService;
import org.assertj.core.util.Lists;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class HomeServiceImplTest {

  private static final String HOME1_ID_VALUE = "5c8fba4cc077d3614023f871";

  @MockBean private HomeDao homeDao;
  @MockBean private HomeConverter homeConverter;
  @MockBean private Environment environment;

  private Home home1;
  private HomeDto dto1;

  private HomeService homeService;

  @Before
  public void setUp() {

    home1 = new Home(new ObjectId(HOME1_ID_VALUE), "HOME1");
    dto1 = new HomeDto(HOME1_ID_VALUE, "HOME1");

    homeService = new HomeServiceImpl(homeDao, homeConverter, environment);
  }

  @Test
  public void findAllHomes() {

    // given
    final Home home2 = new Home(new ObjectId("5c8fba4cc077d3614023f872"), "HOME2");
    final HomeDto dto2 = new HomeDto("5c8fba4cc077d3614023f872", "HOME2");

    final Set<HomeDto> dtoSet = new HashSet<>(2);
    dtoSet.add(dto1);
    dtoSet.add(dto2);

    final HomeSetDto expectedResult = new HomeSetDto(dtoSet);

    Mockito.when(homeDao.findAll()).thenReturn(Lists.list(home1, home2));
    Mockito.when(homeConverter.convertToDto(home1)).thenReturn(dto1);
    Mockito.when(homeConverter.convertToDto(home2)).thenReturn(dto2);

    // when
    final HomeSetDto actualResult = homeService.findAllHomes();

    // then
    assertEquals(expectedResult, actualResult);
  }

  @Test(expected = ObjectNotFoundException.class)
  public void findHomeById_whenHomeNotExist() {

    // given
    Mockito.when(homeDao.existsById(HOME1_ID_VALUE)).thenReturn(false);

    // when
    homeService.findHomeById(HOME1_ID_VALUE);
  }

  @Test
  public void findHomeById_whenHomeExist() {

    // given
    Mockito.when(homeDao.existsById(HOME1_ID_VALUE)).thenReturn(true);
    Mockito.when(homeDao.findHomeById(HOME1_ID_VALUE)).thenReturn(home1);
    Mockito.when(homeConverter.convertToDto(home1)).thenReturn(dto1);
    final HomeDto expectedResult = new HomeDto(HOME1_ID_VALUE, "HOME1");

    // when
    final HomeDto actualResult = homeService.findHomeById(HOME1_ID_VALUE);

    // then
    assertEquals(expectedResult, actualResult);
  }
}

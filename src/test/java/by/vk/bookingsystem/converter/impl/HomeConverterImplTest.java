package by.vk.bookingsystem.converter.impl;

import static org.junit.Assert.assertEquals;

import by.vk.bookingsystem.converter.HomeConverter;
import by.vk.bookingsystem.domain.Home;
import by.vk.bookingsystem.dto.home.HomeDto;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class HomeConverterImplTest {

  private HomeConverter homeConverter;

  @Before
  public void setUp() {
    homeConverter = new HomeConverterImpl();
  }

  @Test
  public void convertToDto() {

    // given
    final Home entity = new Home(new ObjectId("5c8fba4cc077d3614023f871"), "HOME1");
    final HomeDto expectedResult = new HomeDto("5c8fba4cc077d3614023f871", "HOME1");

    // when
    final HomeDto actualResult = homeConverter.convertToDto(entity);

    // then
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void convertToEntity() {
    // given
    final HomeDto dto = new HomeDto("5c8fba4cc077d3614023f871", "HOME1");
    final Home expectedResult = new Home(new ObjectId("5c8fba4cc077d3614023f871"), "HOME1");

    // when
    final Home actualResult = homeConverter.convertToEntity(dto);

    // then
    assertEquals(expectedResult, actualResult);
  }
}

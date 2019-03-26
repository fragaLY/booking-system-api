package by.vk.bookingsystem.converter.impl;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import by.vk.bookingsystem.converter.PriceConverter;
import by.vk.bookingsystem.domain.Price;
import by.vk.bookingsystem.dto.price.PriceDto;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PriceConverterImplTest {

  private PriceConverter priceConverter;

  @Before
  public void setUp() {
    priceConverter = new PriceConverterImpl();
  }

  @Test
  public void convertToDto() {

    // given
    final Price entity = new Price(new ObjectId("5c8fba4cc077d3614023f871"), BigDecimal.TEN, 1);

    final PriceDto expectedResult = new PriceDto("5c8fba4cc077d3614023f871", BigDecimal.TEN, 1);

    // when
    final PriceDto actualResult = priceConverter.convertToDto(entity);

    // then
    assertEquals(expectedResult, actualResult);
  }
}

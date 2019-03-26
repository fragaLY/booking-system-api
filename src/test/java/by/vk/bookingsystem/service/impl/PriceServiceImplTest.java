package by.vk.bookingsystem.service.impl;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import by.vk.bookingsystem.converter.PriceConverter;
import by.vk.bookingsystem.dao.PriceDao;
import by.vk.bookingsystem.domain.Price;
import by.vk.bookingsystem.dto.price.PriceDto;
import by.vk.bookingsystem.dto.price.PriceSetDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.PriceService;
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
public class PriceServiceImplTest {

  private static final String PRICE1_ID_VALUE = "5c8fba4cc077d3614023f871";

  @MockBean private PriceDao priceDao;
  @MockBean private PriceConverter priceConverter;
  @MockBean private Environment environment;

  private Price price1;
  private PriceDto dto1;

  private PriceService priceService;

  @Before
  public void setUp() {
    price1 = new Price(new ObjectId(PRICE1_ID_VALUE), BigDecimal.valueOf(190), 11);
    dto1 = new PriceDto(PRICE1_ID_VALUE, BigDecimal.valueOf(190), 11);
    priceService = new PriceServiceImpl(priceDao, priceConverter, environment);
  }

  @Test
  public void findAllPrices() {

    // given
    final Price price2 =
        new Price(new ObjectId("5c8fba4cc077d3614023f872"), BigDecimal.valueOf(90), 4);

    final PriceDto dto2 = new PriceDto("5c8fba4cc077d3614023f872", BigDecimal.valueOf(90), 4);

    final Set<PriceDto> dtoSet = new HashSet<>(2);
    dtoSet.add(dto1);
    dtoSet.add(dto2);

    final PriceSetDto expectedResult = new PriceSetDto(dtoSet);

    Mockito.when(priceDao.findAll()).thenReturn(Lists.list(price1, price2));
    Mockito.when(priceConverter.convertToDto(price1)).thenReturn(dto1);
    Mockito.when(priceConverter.convertToDto(price2)).thenReturn(dto2);

    // when
    final PriceSetDto actualResult = priceService.findAllPrices();

    // then
    assertEquals(expectedResult, actualResult);
  }

  @Test(expected = ObjectNotFoundException.class)
  public void findPriceById_whenPriceNotExists() {

    // given
    Mockito.when(priceDao.existsById(PRICE1_ID_VALUE)).thenReturn(false);

    // when
    priceService.findPriceById(PRICE1_ID_VALUE);
  }

  @Test
  public void findPriceById_whenPriceExists() {

    // given
    Mockito.when(priceDao.existsById(PRICE1_ID_VALUE)).thenReturn(true);
    Mockito.when(priceDao.findPriceById(PRICE1_ID_VALUE)).thenReturn(price1);
    Mockito.when(priceConverter.convertToDto(price1)).thenReturn(dto1);

    final PriceDto expectedResult = new PriceDto(PRICE1_ID_VALUE, BigDecimal.valueOf(190), 11);

    // when
    final PriceDto actualResult = priceService.findPriceById(PRICE1_ID_VALUE);

    // then
    assertEquals(expectedResult, actualResult);
  }
}

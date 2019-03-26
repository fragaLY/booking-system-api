package by.vk.bookingsystem.service.impl;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import by.vk.bookingsystem.dao.PriceDao;
import by.vk.bookingsystem.domain.Price;
import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.service.CostCalculatorService;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CostCalculatorServiceImplTest {

  @MockBean private PriceDao priceDao;
  @MockBean private Environment environment;

  private Price price;
  private OrderDto orderDto;
  private CostCalculatorService costCalculatorService;

  @Before
  public void setUp() {
    costCalculatorService = new CostCalculatorServiceImpl(priceDao, environment);
    price = new Price(new ObjectId("5c8fba4cc077d3614023f871"), BigDecimal.valueOf(90), 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void calculateCost_whenDurationIsZero() {

    // given
    int guestsAmount = 4;
    final LocalDate now = LocalDate.now();
    orderDto = OrderDto.newBuilder().setFrom(now).setTo(now).setGuests(guestsAmount).build();

    Mockito.when(priceDao.findPriceByGuests(guestsAmount)).thenReturn(price);

    // when
    costCalculatorService.calculateCost(orderDto);
  }

  @Test
  public void calculateCost_positiveCase() {

    // given
    int guestsAmount = 4;
    final LocalDate now = LocalDate.now();
    final LocalDate tomorrow = now.plusDays(1);
    orderDto = OrderDto.newBuilder().setFrom(now).setTo(tomorrow).setGuests(guestsAmount).build();

    Mockito.when(priceDao.findPriceByGuests(guestsAmount)).thenReturn(price);

    final BigDecimal expectedResult = BigDecimal.valueOf(90);
    // when
    final BigDecimal actualResult = costCalculatorService.calculateCost(orderDto);

    // then
    assertEquals(expectedResult, actualResult);
  }
}

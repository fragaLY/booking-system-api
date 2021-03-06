package by.vk.bookingsystem.service.impl;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import by.vk.bookingsystem.converter.OrderConverter;
import by.vk.bookingsystem.dao.OrderDao;
import by.vk.bookingsystem.domain.Order;
import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.dto.order.OrderSetDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.CostCalculatorService;
import by.vk.bookingsystem.service.OrderService;
import by.vk.bookingsystem.validator.order.OrderValidator;
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
public class OrderServiceImplTest {

  private static final String ORDER1_ID_VALUE = "5c8fba4cc077d3614023f871";

  @MockBean private OrderDao orderDao;
  @MockBean private OrderConverter orderConverter;
  @MockBean private OrderValidator orderValidator;
  @MockBean private CostCalculatorService costCalculator;
  @MockBean private Environment environment;

  private Order order1;
  private OrderDto dto1;

  private OrderService orderService;

  @Before
  public void setUp() {
    order1 = Order.builder().id(new ObjectId(ORDER1_ID_VALUE)).build();
    dto1 = OrderDto.newBuilder().setOrderId(ORDER1_ID_VALUE).build();

    orderService =
        new OrderServiceImpl(orderDao, orderConverter, orderValidator, costCalculator, environment);
  }

  @Test
  public void findAllOrders() {

    // given
    final Order order2 = Order.builder().id(new ObjectId("5c8fba4cc077d3614023f872")).build();
    final OrderDto dto2 = OrderDto.newBuilder().setOrderId("5c8fba4cc077d3614023f872").build();

    final Set<OrderDto> dtoSet = new HashSet<>(2);
    dtoSet.add(dto1);
    dtoSet.add(dto2);

    final OrderSetDto expectedResult = new OrderSetDto(dtoSet);

    Mockito.when(orderDao.findAll()).thenReturn(Lists.list(order1, order2));
    Mockito.when(orderConverter.convertToDto(order1)).thenReturn(dto1);
    Mockito.when(orderConverter.convertToDto(order2)).thenReturn(dto2);

    // when
    final OrderSetDto actualResult = orderService.findAllOrders();

    // then
    assertEquals(expectedResult, actualResult);
  }

  @Test(expected = ObjectNotFoundException.class)
  public void findOrderById_whenOrderNotExists() {

    // given
    Mockito.when(orderDao.existsById(ORDER1_ID_VALUE)).thenReturn(false);

    // when
    orderService.findOrderById(ORDER1_ID_VALUE);
  }

  @Test
  public void findOrderById_whenOrderExists() {

    // given
    Mockito.when(orderDao.existsById(ORDER1_ID_VALUE)).thenReturn(true);
    Mockito.when(orderDao.findOrderById(ORDER1_ID_VALUE)).thenReturn(order1);
    Mockito.when(orderConverter.convertToDto(order1)).thenReturn(dto1);

    final OrderDto expectedResult = OrderDto.newBuilder().setOrderId(ORDER1_ID_VALUE).build();

    // when
    final OrderDto actualResult = orderService.findOrderById(ORDER1_ID_VALUE);

    // then
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void createOrder() {

    // given
    Mockito.doNothing().when(orderValidator).validateOwner(dto1.getOwner());
    Mockito.doNothing().when(orderValidator).validateHomes(dto1.getHomes());
    Mockito.doNothing().when(orderValidator).validateOrderDates(dto1);

    Mockito.when(costCalculator.calculateCost(dto1)).thenReturn(BigDecimal.TEN);
    Mockito.when(orderConverter.convertToEntity(dto1)).thenReturn(order1);
    Mockito.when(orderDao.insert(order1)).thenReturn(order1);

    // when
    final String actualResult = orderService.createOrder(dto1);

    // then
    Mockito.verify(orderDao, Mockito.atLeastOnce()).insert(order1);

    assertEquals(ORDER1_ID_VALUE, actualResult);
  }

  @Test(expected = ObjectNotFoundException.class)
  public void updateOrder_whenOrderNotExist() {

    // given
    Mockito.when(orderDao.existsById(ORDER1_ID_VALUE)).thenReturn(false);

    // when
    orderService.updateOrder(dto1, ORDER1_ID_VALUE);
  }

  @Test
  public void updateOrder_whenOrderExists() {

    // given
    Mockito.when(orderDao.existsById(ORDER1_ID_VALUE)).thenReturn(true);
    Mockito.when(orderDao.findOrderById(ORDER1_ID_VALUE)).thenReturn(order1);

    Mockito.doNothing().when(orderValidator).validateOwner(dto1.getOwner());
    Mockito.doNothing().when(orderValidator).validateHomes(dto1.getHomes());
    Mockito.doNothing().when(orderValidator).validateOrderDates(dto1);

    Mockito.when(costCalculator.calculateCost(dto1)).thenReturn(BigDecimal.TEN);

    Mockito.when(orderConverter.enrichModel(order1, dto1)).thenReturn(order1);

    // when
    orderService.updateOrder(dto1, ORDER1_ID_VALUE);
    Mockito.verify(orderDao, Mockito.atLeastOnce()).insert(order1);
  }

  @Test(expected = ObjectNotFoundException.class)
  public void deleteOrderById_whenOrderNotExist() {

    // given
    Mockito.when(orderDao.existsById(ORDER1_ID_VALUE)).thenReturn(false);

    // when
    orderService.deleteOrderById(ORDER1_ID_VALUE);
  }

  @Test
  public void deleteOrderById_whenOrderExist() {

    // given
    Mockito.when(orderDao.existsById(ORDER1_ID_VALUE)).thenReturn(true);

    // when
    orderService.deleteOrderById(ORDER1_ID_VALUE);

    // then
    Mockito.verify(orderDao, Mockito.atLeastOnce()).deleteById(ORDER1_ID_VALUE );
  }
}

package by.vk.bookingsystem.service.impl;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import by.vk.bookingsystem.dao.PriceDao;
import by.vk.bookingsystem.domain.Price;
import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.service.CostCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * The cost calculator service implementation.
 *
 * @author Vadzim_Kavalkou
 */
@Service
@PropertySources(@PropertySource("classpath:i18n/validation_errors.properties"))
public class CostCalculatorServiceImpl implements CostCalculatorService {

  private static final String WRONG_ORDER_DURATION = "order.duration.wrong";

  private final PriceDao priceDao;
  private final Environment environment;

  /**
   * The constructor with parameters.
   *
   * @param priceDao - {@link PriceDao}
   * @param environment - {@link Environment}
   */
  @Autowired
  public CostCalculatorServiceImpl(final PriceDao priceDao, final Environment environment) {
    this.priceDao = priceDao;
    this.environment = environment;
  }

  /**
   * Calculates the cost of order by input values (dates and guests amount).
   *
   * @param dto - {@link OrderDto}
   * @return {@link BigDecimal}
   */
  @Override
  public BigDecimal calculateCost(final OrderDto dto) {
    final LocalTime localTime = LocalTime.of(12, 0, 0, 0);
    final Price price = priceDao.findPriceByGuests(dto.getGuests());
    final Duration duration =
        Duration.between(dto.getFrom().atTime(localTime), dto.getTo().atTime(localTime));

    final long days = duration.toDays();

    if (days == 0) {
      throw new IllegalArgumentException(environment.getProperty(WRONG_ORDER_DURATION));
    }

    return price.getPrice().multiply(BigDecimal.valueOf(days));
  }
}

package by.vk.bookingsystem.service.impl;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import by.vk.bookingsystem.dao.PriceDao;
import by.vk.bookingsystem.domain.Price;
import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.service.CostCalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private static final Logger LOGGER = LoggerFactory.getLogger(CostCalculatorServiceImpl.class);
  private static final LocalTime LOCAL_TIME_MIDDAY = LocalTime.of(12, 0, 0, 0);

  private static final String WRONG_ORDER_DURATION = "order.duration.wrong";

  private static final String COST_ORDER = "The cost for order {} is {}.";
  private static final String WRONG_ORDER_DURATION_LOG = "The order {} has 0 days of duration.";

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
   * @param order - {@link OrderDto}
   * @return {@link BigDecimal}
   */
  @Override
  public BigDecimal calculateCost(final OrderDto order) {
    final Price price = priceDao.findPriceByGuests(order.getGuests());
    final Duration duration =
        Duration.between(
            order.getFrom().atTime(LOCAL_TIME_MIDDAY), order.getTo().atTime(LOCAL_TIME_MIDDAY));

    final long days = duration.toDays();

    if (days == 0) {
      LOGGER.error(WRONG_ORDER_DURATION_LOG, order);
      throw new IllegalArgumentException(environment.getProperty(WRONG_ORDER_DURATION));
    }

    final BigDecimal cost = price.getPricePerPersons().multiply(BigDecimal.valueOf(days));
    LOGGER.debug(COST_ORDER, order, cost);

    return cost;
  }
}

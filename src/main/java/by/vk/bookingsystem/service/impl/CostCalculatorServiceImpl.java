package by.vk.bookingsystem.service.impl;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import by.vk.bookingsystem.dao.PriceDao;
import by.vk.bookingsystem.domain.Price;
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

  private static final String WRONG_ORDER_DURATION_LOG = "0 days of duration.";

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
   * @param from - {@link LocalDate} the start date
   * @param to - {@link LocalDate} the end date
   * @param guests - amount of guests
   * @return {@link BigDecimal}
   */
  @Override
  public BigDecimal calculateCost(final LocalDate from, final LocalDate to, final int guests) {
    final Price price = priceDao.findPriceByGuests(guests);
    final Duration duration =
        Duration.between(from.atTime(LOCAL_TIME_MIDDAY), to.atTime(LOCAL_TIME_MIDDAY));

    final long days = duration.toDays();

    if (days == 0) {
      LOGGER.warn(WRONG_ORDER_DURATION_LOG);
      throw new IllegalArgumentException(environment.getProperty(WRONG_ORDER_DURATION));
    }

    return price.getPricePerPersons().multiply(BigDecimal.valueOf(days));
  }
}

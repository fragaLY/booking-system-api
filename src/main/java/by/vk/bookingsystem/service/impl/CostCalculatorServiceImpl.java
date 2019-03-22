package by.vk.bookingsystem.service.impl;

import java.math.BigDecimal;
import java.time.Duration;

import by.vk.bookingsystem.dao.PriceMongoDao;
import by.vk.bookingsystem.domain.Price;
import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.service.CostCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@PropertySources(@PropertySource("classpath:i18n/validation_errors.properties"))
public class CostCalculatorServiceImpl implements CostCalculatorService {

  private static final String WRONG_ORDER_DURATION = "order.duration.wrong";

  private final PriceMongoDao priceDao;
  private final Environment environment;

  @Autowired
  public CostCalculatorServiceImpl(
      final PriceMongoDao priceMongoDao, final Environment environment) {
    this.priceDao = priceMongoDao;
    this.environment = environment;
  }

  @Override
  public BigDecimal calculateCost(final OrderDto dto) {

    final Price price = priceDao.findPriceByGuests(dto.getGuests());
    final Duration duration =
        Duration.between(dto.getFrom().atTime(12, 0, 0, 0), dto.getTo().atTime(12, 0, 0, 0));

    final long days = duration.toDays();

    if (days == 0) {
      throw new IllegalArgumentException(environment.getProperty(WRONG_ORDER_DURATION));
    }

    return price.getPrice().multiply(BigDecimal.valueOf(days));
  }
}

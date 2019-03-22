package by.vk.bookingsystem.service.impl;

import java.math.BigDecimal;
import java.time.Duration;

import by.vk.bookingsystem.dao.PriceMongoDao;
import by.vk.bookingsystem.domain.Price;
import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.service.CostCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CostCalculatorServiceImpl implements CostCalculatorService {

  private final PriceMongoDao priceDao;

  @Autowired
  public CostCalculatorServiceImpl(final PriceMongoDao priceMongoDao) {
    this.priceDao = priceMongoDao;
  }

  // todo vk:test the solution and find the optimal implementation
  @Override
  public BigDecimal calculateCost(final OrderDto dto) {

    final Price price = priceDao.findPriceByGuests(dto.getGuests());
    final Duration duration = Duration.between(dto.getFrom(), dto.getTo());

    final long days = duration.toDays();
    return price.getPrice().multiply(BigDecimal.valueOf(days));
  }
}

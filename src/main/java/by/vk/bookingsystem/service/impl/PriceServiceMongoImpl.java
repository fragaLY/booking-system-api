package by.vk.bookingsystem.service.impl;

import java.util.Objects;
import java.util.stream.Collectors;

import by.vk.bookingsystem.converter.PriceConverter;
import by.vk.bookingsystem.dao.PriceMongoDao;
import by.vk.bookingsystem.domain.Price;
import by.vk.bookingsystem.dto.price.PriceDto;
import by.vk.bookingsystem.dto.price.PriceSetDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@PropertySources(@PropertySource("classpath:i18n/validation_errors.yml"))
public class PriceServiceMongoImpl implements PriceService {

  private static final String PRICE_NOT_FOUND = "not.found.price";

  private final PriceMongoDao priceDao;
  private final PriceConverter priceConverter;
  private final Environment environment;

  @Autowired
  public PriceServiceMongoImpl(
      final PriceMongoDao priceDao,
      final PriceConverter priceConverter,
      final Environment environment) {
    this.priceDao = priceDao;
    this.priceConverter = priceConverter;
    this.environment = environment;
  }

  @Override
  public PriceSetDto findAllPrices() {
    return new PriceSetDto(
        priceDao.findAll().stream()
            .filter(Objects::nonNull)
            .map(priceConverter::convertToDto)
            .collect(Collectors.toSet()));
  }

  @Override
  public PriceDto findPriceById(final String id) {
    final Price price = priceDao.findPriceById(id);

    if (price == null) {
      throw new ObjectNotFoundException(environment.getProperty(PRICE_NOT_FOUND));
    }

    return priceConverter.convertToDto(price);
  }
}

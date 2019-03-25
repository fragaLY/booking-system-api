package by.vk.bookingsystem.service.impl;

import java.util.Objects;
import java.util.stream.Collectors;

import by.vk.bookingsystem.converter.PriceConverter;
import by.vk.bookingsystem.dao.PriceDao;
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

/**
 * The service implementation for {@link PriceDto}
 *
 * @author Vadzim_Kavalkou
 */
@Service
@PropertySources(@PropertySource("classpath:i18n/validation_errors.properties"))
public class PriceServiceImpl implements PriceService {

  private static final String PRICE_NOT_FOUND = "price.not.found";

  private final PriceDao priceDao;
  private final PriceConverter priceConverter;
  private final Environment environment;

  /**
   * The constructor with parameters.
   *
   * @param priceDao - {@link PriceDao}
   * @param priceConverter - {@link PriceConverter}
   * @param environment - {@link Environment}
   */
  @Autowired
  public PriceServiceImpl(
      final PriceDao priceDao, final PriceConverter priceConverter, final Environment environment) {
    this.priceDao = priceDao;
    this.priceConverter = priceConverter;
    this.environment = environment;
  }

  /**
   * Finds all prices in the system and returns them.
   *
   * @return {@link PriceSetDto}
   */
  @Override
  public PriceSetDto findAllPrices() {
    return new PriceSetDto(
        priceDao.findAll().stream()
            .filter(Objects::nonNull)
            .map(priceConverter::convertToDto)
            .collect(Collectors.toSet()));
  }

  /**
   * Finds the price by its id.
   *
   * @param id - the id of price
   * @return {@link PriceDto}
   */
  @Override
  public PriceDto findPriceById(final String id) {
    final Price price = priceDao.findPriceById(id);

    if (price == null) {
      throw new ObjectNotFoundException(environment.getProperty(PRICE_NOT_FOUND));
    }

    return priceConverter.convertToDto(price);
  }
}

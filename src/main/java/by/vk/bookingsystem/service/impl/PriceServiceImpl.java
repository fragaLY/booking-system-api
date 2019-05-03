package by.vk.bookingsystem.service.impl;

import java.util.Objects;
import java.util.stream.Collectors;

import by.vk.bookingsystem.converter.PriceConverter;
import by.vk.bookingsystem.dao.PriceDao;
import by.vk.bookingsystem.dto.price.PriceDto;
import by.vk.bookingsystem.dto.price.PriceSetDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
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
@CacheConfig(cacheNames = "prices")
@PropertySources(@PropertySource("classpath:i18n/validation_errors.properties"))
public class PriceServiceImpl implements PriceService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PriceServiceImpl.class);

  private static final String PRICE_NOT_FOUND = "price.not.found";

  private static final String PRICE_NOT_FOUND_LOG = "The price with id {} was not found.";

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
  @Cacheable
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
   * <p>If entity with current id is not in the system throws the {@link ObjectNotFoundException}
   *
   * @param id - the id of price
   * @return {@link PriceDto}
   */
  @Cacheable
  @Override
  public PriceDto findPriceById(final String id) {

    if (!priceDao.existsById(id)) {
      LOGGER.warn(PRICE_NOT_FOUND_LOG, id);
      throw new ObjectNotFoundException(environment.getProperty(PRICE_NOT_FOUND));
    }

    return priceConverter.convertToDto(priceDao.findPriceById(id));
  }
}

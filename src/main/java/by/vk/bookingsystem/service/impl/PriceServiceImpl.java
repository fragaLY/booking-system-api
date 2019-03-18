package by.vk.bookingsystem.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import by.vk.bookingsystem.converter.PriceConverter;
import by.vk.bookingsystem.dao.PriceDao;
import by.vk.bookingsystem.domain.Price;
import by.vk.bookingsystem.dto.price.PriceDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl implements PriceService {

  private final PriceDao priceDao;
  private final PriceConverter priceConverter;
  private final Environment environment;

  @Autowired
  public PriceServiceImpl(
      final PriceDao priceDao, final PriceConverter priceConverter, final Environment environment) {
    this.priceDao = priceDao;
    this.priceConverter = priceConverter;
    this.environment = environment;
  }

  @Override
  public List<PriceDto> findAllPrices() {
    return priceDao.findAll().stream()
        .filter(Objects::nonNull)
        .map(priceConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  public PriceDto findPriceById(final String id) {
    final Price price = priceDao.findPriceById(id);

    if (price == null) {
      throw new ObjectNotFoundException(
          environment.getProperty(
              Price.class.getName().toLowerCase()
                  + "."
                  + ObjectNotFoundException.class.getName().toLowerCase()));
    }

    return priceConverter.convertToDto(price);
  }
}

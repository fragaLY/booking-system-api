package by.vk.bookingsystem.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import by.vk.bookingsystem.converter.PriceConverter;
import by.vk.bookingsystem.dao.PriceDao;
import by.vk.bookingsystem.dto.price.PriceDto;
import by.vk.bookingsystem.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl implements PriceService {

  private final PriceDao priceDao;
  private final PriceConverter priceConverter;

  @Autowired
  public PriceServiceImpl(final PriceDao priceDao, final PriceConverter priceConverter) {
    this.priceDao = priceDao;
    this.priceConverter = priceConverter;
  }

  @Override
  public List<PriceDto> findAllPrices() {
    return priceDao.findAll().stream()
        .map(priceConverter::convertToDto)
        .collect(Collectors.toList());
  }
}

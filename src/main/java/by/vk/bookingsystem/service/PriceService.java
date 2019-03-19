package by.vk.bookingsystem.service;

import by.vk.bookingsystem.dto.price.PriceDto;
import by.vk.bookingsystem.dto.price.PriceSetDto;

public interface PriceService {

  PriceSetDto findAllPrices();

  PriceDto findPriceById(String id);
}

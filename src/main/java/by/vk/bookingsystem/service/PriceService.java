package by.vk.bookingsystem.service;

import java.util.List;

import by.vk.bookingsystem.dto.price.PriceDto;

public interface PriceService {

    List<PriceDto> findAllPrices();
}

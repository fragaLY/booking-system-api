package by.vk.bookingsystem.service;

import by.vk.bookingsystem.domain.Price;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The service for prices
 *
 * @author Vadzim_Kavalkou
 */
public interface PriceService {

  Flux<Price> findAllPrices();

  Mono<Price> findPriceById(String id);
}

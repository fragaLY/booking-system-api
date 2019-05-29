package by.vk.bookingsystem.service;

import by.vk.bookingsystem.domain.Home;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The service for homes
 *
 * @author Vadzim_Kavalkou
 */
public interface HomeService {

  Flux<Home> findAllHomes();

  Mono<Home> findHomeById(String id);
}

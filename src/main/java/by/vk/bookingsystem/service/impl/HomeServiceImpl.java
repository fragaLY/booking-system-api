package by.vk.bookingsystem.service.impl;

import java.util.Objects;
import java.util.stream.Collectors;

import by.vk.bookingsystem.converter.HomeConverter;
import by.vk.bookingsystem.dao.HomeDao;
import by.vk.bookingsystem.domain.Home;
import by.vk.bookingsystem.dto.home.HomeDto;
import by.vk.bookingsystem.dto.home.HomeSetDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * The service implementation for {@link HomeDto}.
 *
 * @author Vadzim_Kavalkou
 */
@Service
@PropertySources(@PropertySource("classpath:i18n/validation_errors.properties"))
public class HomeServiceImpl implements HomeService {

  private static final String HOME_NOT_FOUND = "home.not.found";

  private final HomeDao homeDao;
  private final HomeConverter homeConverter;
  private final Environment environment;

  /**
   * The constructor with parameters.
   *
   * @param homeDao - {@link HomeDao}
   * @param homeConverter - {@link HomeConverter}
   * @param environment - {@link Environment}
   */
  @Autowired
  public HomeServiceImpl(
      final HomeDao homeDao, final HomeConverter homeConverter, final Environment environment) {
    this.homeDao = homeDao;
    this.homeConverter = homeConverter;
    this.environment = environment;
  }

  /**
   * Finds all homes in the system and returns them.
   *
   * @return {@link HomeSetDto}
   */
  @Override
  public HomeSetDto findAllHomes() {
    return new HomeSetDto(
        homeDao.findAll().stream()
            .filter(Objects::nonNull)
            .map(homeConverter::convertToDto)
            .collect(Collectors.toSet()));
  }

  /**
   * Finds the home by id.
   *
   * @param id - the id of home.
   * @return {@link HomeDto}
   */
  @Override
  public HomeDto findHomeById(final String id) {
    final Home home = homeDao.findHomeById(id);

    if (home == null) {
      throw new ObjectNotFoundException(environment.getProperty(HOME_NOT_FOUND));
    }

    return homeConverter.convertToDto(home);
  }
}

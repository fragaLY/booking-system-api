package by.vk.bookingsystem.service.impl;

import java.util.Objects;
import java.util.stream.Collectors;

import by.vk.bookingsystem.converter.HomeConverter;
import by.vk.bookingsystem.dao.HomeMongoDao;
import by.vk.bookingsystem.domain.Home;
import by.vk.bookingsystem.dto.home.HomeDto;
import by.vk.bookingsystem.dto.home.HomeSetDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceMongoImpl implements HomeService {

  private static final String HOME_NOT_FOUND = "not.found.home";

  private final HomeMongoDao homeDao;
  private final HomeConverter homeConverter;
  private final Environment environment;

  @Autowired
  public HomeServiceMongoImpl(
      final HomeMongoDao homeDao,
      final HomeConverter homeConverter,
      final Environment environment) {
    this.homeDao = homeDao;
    this.homeConverter = homeConverter;
    this.environment = environment;
  }

  @Override
  public HomeSetDto findAllHomes() {
    return new HomeSetDto(
        homeDao.findAll().stream()
            .filter(Objects::nonNull)
            .map(homeConverter::convertToDto)
            .collect(Collectors.toSet()));
  }

  @Override
  public HomeDto findHomeById(final String id) {
    final Home home = homeDao.findHomeById(id);

    if (home == null) {
      throw new ObjectNotFoundException(environment.getProperty(HOME_NOT_FOUND));
    }

    return homeConverter.convertToDto(home);
  }
}

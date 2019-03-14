package by.vk.bookingsystem.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import by.vk.bookingsystem.converter.HomeConverter;
import by.vk.bookingsystem.dao.HomeDao;
import by.vk.bookingsystem.dto.home.HomeDto;
import by.vk.bookingsystem.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {

  private final HomeDao homeDao;
  private final HomeConverter homeConverter;

  @Autowired
  public HomeServiceImpl(final HomeDao homeDao, final HomeConverter homeConverter) {
    this.homeDao = homeDao;
    this.homeConverter = homeConverter;
  }

  @Override
  public List<HomeDto> findAll() {
    return homeDao.findAll().stream().map(homeConverter::convertToDto).collect(Collectors.toList());
  }
}

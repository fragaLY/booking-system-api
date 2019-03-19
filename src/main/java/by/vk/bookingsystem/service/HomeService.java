package by.vk.bookingsystem.service;

import by.vk.bookingsystem.dto.home.HomeDto;
import by.vk.bookingsystem.dto.home.HomeSetDto;

public interface HomeService {

  HomeSetDto findAllHomes();

  HomeDto findHomeById(String id);
}

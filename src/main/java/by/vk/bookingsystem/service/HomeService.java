package by.vk.bookingsystem.service;

import java.util.List;

import by.vk.bookingsystem.dto.home.HomeDto;

public interface HomeService {

  List<HomeDto> findAllHomes();

  HomeDto findHomeById(String id);
}

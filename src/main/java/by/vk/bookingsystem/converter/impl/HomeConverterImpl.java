package by.vk.bookingsystem.converter.impl;

import by.vk.bookingsystem.converter.HomeConverter;
import by.vk.bookingsystem.domain.Home;
import by.vk.bookingsystem.dto.home.HomeDto;
import org.springframework.stereotype.Component;

@Component
public class HomeConverterImpl implements HomeConverter {

  @Override
  public HomeDto convertToDto(final Home entity) {
    return HomeDto.builder()
        .id(entity.getId())
        .name(entity.getName())
        .price(entity.getPrice())
        .build();
  }

  @Override
  public Home convertToEntity(final HomeDto dto) {
    return Home.builder().id(dto.getId()).name(dto.getName()).price(dto.getPrice()).build();
  }
}

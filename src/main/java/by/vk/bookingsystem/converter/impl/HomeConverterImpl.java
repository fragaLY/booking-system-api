package by.vk.bookingsystem.converter.impl;

import by.vk.bookingsystem.converter.HomeConverter;
import by.vk.bookingsystem.domain.Home;
import by.vk.bookingsystem.dto.home.HomeDto;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class HomeConverterImpl implements HomeConverter {

  @Override
  public HomeDto convertToDto(final Home entity) {
    return HomeDto.builder()
        .id(entity.getId().toHexString())
        .name(entity.getName())
        .price(entity.getPrice())
        .build();
  }

  @Override
  public Home convertToEntity(final HomeDto dto) {
    return Home.builder()
        .id(new ObjectId(dto.getId()))
        .name(dto.getName())
        .price(dto.getPrice())
        .build();
  }
}

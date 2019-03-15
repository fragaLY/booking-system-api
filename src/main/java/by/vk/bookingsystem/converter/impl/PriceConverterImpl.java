package by.vk.bookingsystem.converter.impl;

import by.vk.bookingsystem.converter.PriceConverter;
import by.vk.bookingsystem.domain.Price;
import by.vk.bookingsystem.dto.price.PriceDto;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class PriceConverterImpl implements PriceConverter {

  @Override
  public PriceDto convertToDto(final Price entity) {
    return new PriceDto(entity.getId().toHexString(), entity.getPrice(), entity.getGuests());
  }

  @Override
  public Price convertToEntity(final PriceDto dto) {
    return new Price(new ObjectId(dto.getId()), dto.getPrice(), dto.getGuests());
  }
}

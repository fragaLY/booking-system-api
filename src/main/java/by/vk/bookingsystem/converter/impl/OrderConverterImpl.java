package by.vk.bookingsystem.converter.impl;

import by.vk.bookingsystem.converter.OrderConverter;
import by.vk.bookingsystem.domain.Order;
import by.vk.bookingsystem.dto.order.OrderDto;
import org.springframework.stereotype.Component;

@Component
public class OrderConverterImpl implements OrderConverter {

  @Override
  public OrderDto convertToDto(final Order entity) {

    return new OrderDto().new Builder()
        .setId(entity.getId().toHexString())
        .setFrom(entity.getFrom())
        .setTo(entity.getTo())
        .setCost(entity.getCost())
        .setConfirmed(entity.isConfirmed())
        .setHomeIds(entity.getHomes())
        .setOwnerId(entity.getOwnerId())
        .build();
  }

  @Override
  public Order convertToEntity(final OrderDto dto) {

    return Order.builder()
        .from(dto.getFrom())
        .to(dto.getTo())
        .cost(dto.getCost())
        .confirmed(dto.isConfirmed())
        .homes(dto.getHomeIds())
        .ownerId(dto.getOwnerId())
        .build();
  }

  @Override
  public Order enrichModel(final Order order, final OrderDto dto) {
    order.setConfirmed(dto.isConfirmed());
    order.setCost(dto.getCost());
    order.setFrom(dto.getFrom());
    order.setTo(dto.getTo());
    order.setGuests(dto.getGuests());
    order.setHomes(dto.getHomeIds());
    return order;
  }
}

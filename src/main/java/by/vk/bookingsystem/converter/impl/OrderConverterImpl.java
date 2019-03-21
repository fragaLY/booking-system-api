package by.vk.bookingsystem.converter.impl;

import java.util.Objects;
import java.util.stream.Collectors;

import by.vk.bookingsystem.converter.HomeConverter;
import by.vk.bookingsystem.converter.OrderConverter;
import by.vk.bookingsystem.converter.UserConverter;
import by.vk.bookingsystem.domain.Order;
import by.vk.bookingsystem.dto.order.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderConverterImpl implements OrderConverter {

  private final HomeConverter homeConverter;
  private final UserConverter userConverter;

  @Autowired
  public OrderConverterImpl(final HomeConverter homeConverter, final UserConverter userConverter) {
    this.homeConverter = homeConverter;
    this.userConverter = userConverter;
  }

  @Override
  public OrderDto convertToDto(final Order entity) {

    return new OrderDto().new Builder()
        .setId(entity.getId().toHexString())
        .setFrom(entity.getFrom())
        .setTo(entity.getTo())
        .setCost(entity.getCost())
        .setConfirmed(entity.isConfirmed())
        .setHomes(
            entity.getHomes().stream()
                .filter(Objects::nonNull)
                .map(homeConverter::convertToDto)
                .collect(Collectors.toSet()))
        .setOwner(userConverter.convertToDto(entity.getOwner()))
        .setGuests(entity.getGuests())
        .build();
  }

  @Override
  public Order convertToEntity(final OrderDto dto) {

    return Order.builder()
        .from(dto.getFrom())
        .to(dto.getTo())
        .cost(dto.getCost())
        .confirmed(dto.isConfirmed())
        .homes(
            dto.getHomes().stream()
                .filter(Objects::nonNull)
                .map(homeConverter::convertToEntity)
                .collect(Collectors.toSet()))
        .owner(userConverter.convertToEntity(dto.getOwner()))
        .build();
  }

  @Override
  public Order enrichModel(final Order order, final OrderDto dto) {
    order.setConfirmed(dto.isConfirmed());
    order.setCost(dto.getCost());
    order.setFrom(dto.getFrom());
    order.setTo(dto.getTo());
    order.setGuests(dto.getGuests());
    order.setHomes(
        dto.getHomes().stream()
            .filter(Objects::nonNull)
            .map(homeConverter::convertToEntity)
            .collect(Collectors.toSet()));
    return order;
  }
}

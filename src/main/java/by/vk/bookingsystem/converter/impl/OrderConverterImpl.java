package by.vk.bookingsystem.converter.impl;

import java.util.Objects;
import java.util.stream.Collectors;

import by.vk.bookingsystem.converter.HomeConverter;
import by.vk.bookingsystem.converter.OrderConverter;
import by.vk.bookingsystem.converter.UserConverter;
import by.vk.bookingsystem.domain.Order;
import by.vk.bookingsystem.dto.order.OrderDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The converter implementation for {@link Order} and {@link OrderDto}
 *
 * @author Vadzim_Kavalkou
 */
@Component
public class OrderConverterImpl implements OrderConverter {

  private final HomeConverter homeConverter;
  private final UserConverter userConverter;

  /**
   * The constructor of class. Uses autowiring via it.
   *
   * @param homeConverter - the {@link HomeConverter} implementation
   * @param userConverter - the {@link UserConverter} implementation
   */
  @Autowired
  public OrderConverterImpl(final HomeConverter homeConverter, final UserConverter userConverter) {
    this.homeConverter = homeConverter;
    this.userConverter = userConverter;
  }

  /**
   * Converts the entity to dto.
   *
   * @param entity - {@link Order}
   * @return {@link OrderDto}
   */
  @Override
  public OrderDto convertToDto(final Order entity) {

    return OrderDto.newBuilder()
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

  /**
   * Converts the dto to entity.
   *
   * @param dto - {@link OrderDto}
   * @return {@link Order}
   */
  @Override
  public Order convertToEntity(final OrderDto dto) {

    return Order.builder()
        .id(dto.getId() != null ? new ObjectId(dto.getId()) : null)
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
        .guests(dto.getGuests())
        .build();
  }

  /**
   * Enriches the entity with new information from data transfer object.
   *
   * @param order - {@link Order}
   * @param dto - {@link OrderDto}
   * @return {@link Order}
   */
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

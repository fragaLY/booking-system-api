package by.vk.bookingsystem.converter;

import by.vk.bookingsystem.domain.Order;
import by.vk.bookingsystem.dto.order.OrderDto;

public interface OrderConverter {

  /**
   * Converts entity to data transfer object
   *
   * @param entity - the entity
   * @return the {@link OrderDto}
   */
  OrderDto convertToDto(Order entity);

  /**
   * Converts dto to entity
   *
   * @param dto - the data transfer object
   * @return the {@link Order}
   */
  Order convertToEntity(OrderDto dto);
}

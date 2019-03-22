package by.vk.bookingsystem.converter;

import by.vk.bookingsystem.domain.Order;
import by.vk.bookingsystem.dto.order.OrderDto;

/**
 * The converter for {@link Order} and {@link OrderDto}
 *
 * @author Vadzim_Kavalkou
 */
public interface OrderConverter {

  /**
   * Converts entity to data transfer object
   *
   * @param entity - {@link Order}
   * @see by.vk.bookingsystem.converter.impl.OrderConverterImpl
   * @return the {@link OrderDto}
   */
  OrderDto convertToDto(Order entity);

  /**
   * Converts dto to entity
   *
   * @param dto - {@link OrderDto}
   * @see by.vk.bookingsystem.converter.impl.OrderConverterImpl
   * @return {@link Order}
   */
  Order convertToEntity(OrderDto dto);

  /**
   * Enriches the entity with new information from data transfer object
   *
   * @param order - {@link Order}
   * @param dto - {@link OrderDto}
   * @see by.vk.bookingsystem.converter.impl.OrderConverterImpl
   * @return {@link Order}
   */
  Order enrichModel(Order order, OrderDto dto);
}

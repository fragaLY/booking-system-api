package by.vk.bookingsystem.validator.order;

import java.util.Set;

import by.vk.bookingsystem.dto.home.HomeDto;
import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.dto.user.UserDto;

public interface OrderValidator {

    void validateOwner(UserDto owner);

    void validateHomes(Set<HomeDto> homes);

    void validateOrderDates(OrderDto order);
}

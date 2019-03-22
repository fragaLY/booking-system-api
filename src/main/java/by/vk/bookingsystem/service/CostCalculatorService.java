package by.vk.bookingsystem.service;

import java.math.BigDecimal;

import by.vk.bookingsystem.dto.order.OrderDto;

public interface CostCalculatorService {

  BigDecimal calculateCost(OrderDto dto);
}

package by.vk.bookingsystem.validator.order.impl;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import by.vk.bookingsystem.dao.HomeMongoDao;
import by.vk.bookingsystem.dao.OrderMongoDao;
import by.vk.bookingsystem.dao.UserMongoDao;
import by.vk.bookingsystem.domain.Home;
import by.vk.bookingsystem.domain.Order;
import by.vk.bookingsystem.dto.home.HomeDto;
import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.dto.user.UserDto;
import by.vk.bookingsystem.validator.order.OrderValidator;
import com.google.common.collect.Lists;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySources(@PropertySource("classpath:i18n/validation_errors.properties"))
public class OrderValidatorImpl implements OrderValidator {

    private static final String OWNER_NOT_FOUND = "owner.not.found";
    private static final String HOMES_NOT_FOUND = "homes.not.found";
    private static final String INTERSECTING_DATES = "order.dates.intersection";

    private final OrderMongoDao orderDao;
    private final UserMongoDao userDao;
    private final HomeMongoDao homeDao;
    private final Environment environment;

    @Autowired
    public OrderValidatorImpl(
            final UserMongoDao userDao,
            final HomeMongoDao homeDao,
            final OrderMongoDao orderDao,
            final Environment environment) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.homeDao = homeDao;
        this.environment = environment;
    }

    public void validateOwner(final UserDto owner) {
        if (userDao.findUserById(owner.getId()) == null) {
            throw new IllegalArgumentException(environment.getProperty(OWNER_NOT_FOUND));
        }
    }

    public void validateHomes(final Set<HomeDto> homes) {
        final List<Home> validHomes =
                Lists.newArrayList(
                        homeDao.findAllById(
                                homes.stream()
                                        .filter(Objects::nonNull)
                                        .map(HomeDto::getId)
                                        .map(ObjectId::new)
                                        .collect(Collectors.toSet())));

        if (validHomes.isEmpty()) {
            throw new IllegalArgumentException(environment.getProperty(HOMES_NOT_FOUND));
        }
    }

    @Override

    // todo vk: rework solution
    public void validateOrderDates(final OrderDto order) {
        final List<Order> intersecting =
                orderDao.findOrdersByFromBetweenAndToBetween(
                        order.getFrom().toLocalDate(), order.getTo().toLocalDate());

        if (intersecting != null && !intersecting.isEmpty()) {
            throw new IllegalArgumentException(environment.getProperty(INTERSECTING_DATES));
        }
    }
}

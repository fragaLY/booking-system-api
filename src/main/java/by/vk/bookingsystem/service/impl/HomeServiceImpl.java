package by.vk.bookingsystem.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import by.vk.bookingsystem.converter.HomeConverter;
import by.vk.bookingsystem.dao.HomeDao;
import by.vk.bookingsystem.domain.Home;
import by.vk.bookingsystem.dto.home.HomeDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {

    private final HomeDao homeDao;
    private final HomeConverter homeConverter;
    private final Environment environment;

    @Autowired
    public HomeServiceImpl(
            final HomeDao homeDao, final HomeConverter homeConverter, final Environment environment) {
        this.homeDao = homeDao;
        this.homeConverter = homeConverter;
        this.environment = environment;
    }

    @Override
    public List<HomeDto> findAllHomes() {
        return homeDao.findAll().stream()
                .filter(Objects::nonNull)
                .map(homeConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public HomeDto findHomeById(final String id) {
        final Home home = homeDao.findHomeById(id);

        if (home == null) {
            throw new ObjectNotFoundException(
                    environment.getProperty(
                            Home.class.getName().toLowerCase()
                                    + "."
                                    + ObjectNotFoundException.class.getName().toLowerCase()));
        }

        return homeConverter.convertToDto(home);
    }
}

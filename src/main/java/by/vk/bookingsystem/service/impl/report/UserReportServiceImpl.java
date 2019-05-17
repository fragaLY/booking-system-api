package by.vk.bookingsystem.service.impl.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import by.vk.bookingsystem.converter.UserConverter;
import by.vk.bookingsystem.dao.UserDao;
import by.vk.bookingsystem.domain.User;
import by.vk.bookingsystem.dto.user.UserDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.report.UsersWordDocument;
import by.vk.bookingsystem.report.WordDocument;
import by.vk.bookingsystem.report.setting.ReportSettings;
import by.vk.bookingsystem.report.setting.ReportType;
import by.vk.bookingsystem.service.ReportService;
import lombok.SneakyThrows;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

/**
 * The service for user reporting
 *
 * @author Vadzim_Kavalkou
 */
@Service("userReportService")
@PropertySources(@PropertySource("classpath:i18n/validation_errors.properties"))
public class UserReportServiceImpl implements ReportService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserReportServiceImpl.class);

  private static final String INVALID_DATES = "dates.invalid";
  private static final String USERS_NOT_FOUND_LOG = "The users were not found from {} and {}";
  private static final String USERS_NOT_FOUND = "The users were not found from %s and %s";
  private static final String INVALID_REPORT_DATES_LOG =
      "The from date should be before to date for report";

  private final UserDao userDao;
  private final UserConverter userConverter;
  private final Environment environment;
  private final ResourceLoader resourceLoader;

  /**
   * The constructor with parameters.
   *
   * @param userDao {@link UserDao}
   * @param userConverter {@link UserConverter}
   * @param environment {@link Environment}
   * @param resourceLoader {@link ResourceLoader}
   */
  @Autowired
  public UserReportServiceImpl(
      final UserDao userDao,
      final UserConverter userConverter,
      final Environment environment,
      final ResourceLoader resourceLoader) {
    this.userDao = userDao;
    this.userConverter = userConverter;
    this.environment = environment;
    this.resourceLoader = resourceLoader;
  }

  /**
   * Generates users report between selected dates
   *
   * @param from {@link LocalDate}
   * @param to {@link LocalDate}
   */
  @SneakyThrows(IOException.class)
  @Override
  public Resource generateReportResource(final LocalDate from, final LocalDate to) {

    if (from.isAfter(to)) {
      LOGGER.warn(INVALID_REPORT_DATES_LOG);
      throw new IllegalArgumentException(environment.getProperty(INVALID_DATES));
    }

    final List<User> users = userDao.findUsersRegisteredBetweenDates(from, to);

    if (users == null || users.isEmpty()) {
      LOGGER.warn(USERS_NOT_FOUND_LOG, from, to);
      throw new ObjectNotFoundException(String.format(USERS_NOT_FOUND, from, to));
    }

    final List<UserDto> userDtos =
        users.stream()
            .filter(Objects::nonNull)
            .map(userConverter::convertToDto)
            .collect(Collectors.toList());

    final LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

    final WordDocument wordDocument;
    final Resource resource = resourceLoader.getResource(ReportSettings.LOGO_RESOURCE.getValue());

    try (final InputStream imageInputStream = resource.getInputStream()) {
      wordDocument =
          new UsersWordDocument(new XWPFDocument(), userDtos)
              .addTableHeader(UsersWordDocument.USER_HEADERS)
              .addTableRows()
              .addImage(imageInputStream)
              .addFooter(from, to, userDtos.size(), ReportType.USERS, now);
    }

    byte[] outputByteArray;

    try (final XWPFDocument document = wordDocument.getDocument();
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
      document.write(byteArrayOutputStream);
      outputByteArray = byteArrayOutputStream.toByteArray();
    }

    return new ByteArrayResource(outputByteArray);
  }
}

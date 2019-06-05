package by.vk.bookingsystem.service;

import java.time.LocalDate;

import by.vk.bookingsystem.report.WordDocument;
import org.springframework.core.io.Resource;

/**
 * The service for reporting
 *
 * @author Vadzim_Kavalkou
 */
public interface ReportService {

  /**
   * Generates the report between selected dates or by default last day.
   *
   * @param from {@link LocalDate}
   * @param to {@link LocalDate}
   * @return {@link WordDocument}
   */
  Resource generateReportResource(LocalDate from, LocalDate to);
}

package by.vk.bookingsystem.report.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The report types
 *
 * @author Vadzim_Kavalkou
 */
@Getter
@AllArgsConstructor
public enum ReportType {
  USERS("user(s)"),
  ORDERS("order(s)");

  private final String type;
}

package by.vk.bookingsystem.report.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The report settings
 *
 * @author Vadzim_Kavalkou
 */
@Getter
@AllArgsConstructor
public enum ReportSettings {
  LOGO_RESOURCE("classpath:poi/booking-system-logo.png"),
  FONT_FAMILY_COURIER("Courier"),
  BLUE_COLOR("3fb8ff"),
  BLACK_COLOR("000000"),
  TITLE_MESSAGE("%s Report"),
  SUBTITLE_MESSAGE("%d %s registered between '%s' and '%s'"),
  FOOTER_MESSAGE("Generated at '%s'");

  private final String value;
}

package by.vk.bookingsystem.report;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.IntSummaryStatistics;
import java.util.LongSummaryStatistics;
import java.util.Map;

import by.vk.bookingsystem.report.setting.ReportSettings;
import by.vk.bookingsystem.report.setting.ReportType;
import by.vk.bookingsystem.report.statistics.CostStatistics;
import lombok.SneakyThrows;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * Abstract word document
 *
 * @author Vadzim_Kavalkou
 */
public abstract class WordDocument {

  private static final String DATE_TIME_FORMATTER_PATTERN = "yyyy-MM-dd HH-mm-ss";

  static final String SUMMARY = "Summary";
  static final String AVERAGE = "Average";

  int rowIndex;

  private final XWPFDocument document;
  final XWPFTable table;

  /**
   * The constructor of class
   *
   * @param document {@link XWPFDocument}
   * @param rows the rows in table
   * @param columns the columns in table
   */
  WordDocument(final XWPFDocument document, final int rows, final int columns) {
    this.document = document;
    this.table = document.createTable(rows, columns);
  }

  /**
   * Returns *.docx document
   *
   * @return {@link XWPFDocument}
   */
  public XWPFDocument getDocument() {
    return document;
  }

  /**
   * Add header to table
   *
   * @param headers the table header information
   * @return {@link WordDocument}
   */
  public WordDocument addTableHeader(final Map<Integer, String> headers) {

    final XWPFTableRow headerRow = table.getRow(rowIndex++);
    headers.forEach((key, value) -> headerRow.getCell(key).setText(value));

    return this;
  }

  /**
   * Add footer to document
   *
   * @param from {@link LocalDate}
   * @param to {@link LocalDate}
   * @param amountOfObjects amount of objects for report
   * @param type {@link ReportType}
   * @param generationTime {@link LocalDateTime}
   * @return {@link WordDocument}
   */
  public WordDocument addFooter(
      final LocalDate from,
      final LocalDate to,
      final int amountOfObjects,
      final ReportType type,
      final LocalDateTime generationTime) {
    final XWPFParagraph footerInfo = document.createParagraph();
    footerInfo.setAlignment(ParagraphAlignment.RIGHT);

    final XWPFRun subTitleRun = footerInfo.createRun();
    subTitleRun.setText(
        String.format(
            ReportSettings.SUBTITLE_MESSAGE.getValue(), amountOfObjects, type.getType(), from, to));

    final XWPFParagraph footer = document.createParagraph();
    footer.setAlignment(ParagraphAlignment.RIGHT);

    final XWPFRun footerRun = footer.createRun();
    final DateTimeFormatter dateTimeFormatter =
        DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER_PATTERN);
    footerRun.setText(
        String.format(
            ReportSettings.FOOTER_MESSAGE.getValue(), dateTimeFormatter.format(generationTime)));

    return this;
  }

  /**
   * Add logo image to report
   *
   * @return {@link WordDocument}
   */
  @SneakyThrows({IOException.class, InvalidFormatException.class})
  public WordDocument addImage() {

    final XWPFParagraph image = document.createParagraph();
    image.setAlignment(ParagraphAlignment.CENTER);

    final File imageFile = new File(ReportSettings.LOGO_PATH_WINDOWS.getValue());
    final DataInputStream imageDataStream = new DataInputStream(new FileInputStream(imageFile));

    final XWPFRun imageRun = image.createRun();

    imageRun.addPicture(
        imageDataStream,
        XWPFDocument.PICTURE_TYPE_PNG,
        imageFile.getName(),
        Units.toEMU(132),
        Units.toEMU(40));

    return this;
  }

  /**
   * Add table rows with needed information
   *
   * @return {@link WordDocument}
   */
  public abstract WordDocument addTableRows();

  /**
   * Add average statistics for report
   *
   * @param durationStatistics {@link LongSummaryStatistics}
   * @param costStatistics {@link CostStatistics}
   * @param guestsStatistics {@link IntSummaryStatistics}
   * @return {@link WordDocument}
   */
  public abstract WordDocument addAverageStatistics(
      LongSummaryStatistics durationStatistics,
      CostStatistics costStatistics,
      IntSummaryStatistics guestsStatistics);

  /**
   * Add summary statistics for report
   *
   * @param durationStatistics {@link LongSummaryStatistics}
   * @param costStatistics {@link CostStatistics}
   * @param guestsStatistics {@link IntSummaryStatistics}
   * @return {@link WordDocument}
   */
  public abstract WordDocument addSummaryStatistics(
      LongSummaryStatistics durationStatistics,
      CostStatistics costStatistics,
      IntSummaryStatistics guestsStatistics);
}

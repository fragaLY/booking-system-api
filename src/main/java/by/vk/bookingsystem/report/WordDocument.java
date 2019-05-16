package by.vk.bookingsystem.report;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import by.vk.bookingsystem.report.setting.ReportSettings;
import by.vk.bookingsystem.report.setting.ReportType;
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
    footerRun.setText(String.format(ReportSettings.FOOTER_MESSAGE.getValue(), generationTime));

    return this;
  }

  /**
   * Add logo image to report
   *
   * @return {@link WordDocument}
   */
  @SneakyThrows({URISyntaxException.class, IOException.class, InvalidFormatException.class})
  public WordDocument addImage() {

    final XWPFParagraph image = document.createParagraph();
    image.setAlignment(ParagraphAlignment.CENTER);

    final XWPFRun imageRun = image.createRun();
    final Path imagePath =
        Paths.get(ClassLoader.getSystemResource(ReportSettings.LOGO_PATH.getValue()).toURI());
    imageRun.addPicture(
        Files.newInputStream(imagePath),
        XWPFDocument.PICTURE_TYPE_PNG,
        imagePath.getFileName().toString(),
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
}

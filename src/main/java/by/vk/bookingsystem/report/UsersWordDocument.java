package by.vk.bookingsystem.report;

import java.util.List;
import java.util.Map;

import by.vk.bookingsystem.dto.user.UserDto;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * Users word document
 *
 * @author Vadzim_Kavalkou
 */
@Getter
public final class UsersWordDocument extends WordDocument {

  public static final Map<Integer, String> USER_HEADERS =
      ImmutableMap.<Integer, String>builder()
          .put(0, "#")
          .put(1, "Name")
          .put(2, "Email")
          .put(3, "Phone")
          .put(4, "Country")
          .put(5, "City")
          .put(6, "Registered")
          .build();

  private final List<UserDto> users;

  /**
   * The constructor of class
   *
   * @param document {@link XWPFDocument}
   * @param users {@link List} of {@link UserDto}
   */
  public UsersWordDocument(final XWPFDocument document, final List<UserDto> users) {
    super(document, users.size() + 1, USER_HEADERS.size());
    this.users = users;
  }

  /**
   * Enriches the document with users information
   *
   * @return {@link UsersWordDocument}
   */
  // todo vk: refactor this peace of shit
  public UsersWordDocument addTableRows() {

    users.forEach(
        user -> {
          final XWPFTableRow row = table.getRow(rowIndex);
          row.getCell(0).setText(String.valueOf(rowIndex++));
          row.getCell(1).setText(user.getLastName().concat(" ").concat(user.getFirstName()));
          row.getCell(2).setText(user.getEmail());
          row.getCell(3).setText(user.getPhone());
          row.getCell(4).setText(user.getCountry());
          row.getCell(5).setText(user.getCity());
          row.getCell(6).setText(user.getRegistered().toString());
        });

    return this;
  }
}

package by.vk.bookingsystem.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * The entity of order.
 *
 * @author Vadzim_Kavalkou
 */
@Document(collection = "order")
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"owner", "homes"})
public class Order {

  @Id private ObjectId id;

  @DBRef(lazy = true)
  private User owner;

  @DBRef(lazy = true)
  private Set<Home> homes;

  private LocalDate from;
  private LocalDate to;
  private BigDecimal cost;
  private boolean confirmed;
  private int guests;
}

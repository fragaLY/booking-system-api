package by.vk.bookingsystem.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
@Getter
@Setter
@ToString(exclude = {"homes", "user"})
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"id", "homes", "user"})
public class Order {

  @Id private final ObjectId id;

  private final LocalDateTime from;
  private final LocalDateTime to;
  private final BigDecimal cost;
  private final boolean confirmed;
  private final Set<Home> homes;
  private final User user;
}

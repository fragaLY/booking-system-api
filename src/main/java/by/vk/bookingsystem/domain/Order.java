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

@Document(collection = "order")
@Getter
@Setter
@ToString(exclude = {"homes", "user"})
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"homes", "user"})
public class Order {

  @Id private final ObjectId id;

  private User user;
  private LocalDateTime from;
  private LocalDateTime to;
  private BigDecimal cost;
  private boolean confirmed;
  private Set<Home> homes;
  private byte guests;
}

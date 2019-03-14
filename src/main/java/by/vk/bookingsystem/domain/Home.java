package by.vk.bookingsystem.domain;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "homes")
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode(exclude = "id")
public class Home {

  @Id
  private final ObjectId id;
  private final String name;
  private final BigDecimal price;
}

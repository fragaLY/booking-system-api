package by.vk.bookingsystem.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "price")
@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class Price {

  @Id private final ObjectId id;
  private final BigDecimal price;
  private final byte guests;
}

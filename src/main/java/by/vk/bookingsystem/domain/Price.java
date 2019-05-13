package by.vk.bookingsystem.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * The entity of price.
 *
 * @author Vadzim_Kavalkou
 */
@Document(collection = "price")
@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class Price {

  @Id private final ObjectId id;

  @Field("price")
  private final BigDecimal pricePerPersons;

  @Indexed(unique = true)
  private final int guests;
}

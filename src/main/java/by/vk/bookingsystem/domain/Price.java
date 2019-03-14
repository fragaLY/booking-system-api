package by.vk.bookingsystem.domain;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "price")
@Getter
@ToString
@RequiredArgsConstructor
public class Price {

  @Id private final ObjectId id;

  @BsonProperty("price")
  private final BigDecimal pricePerAmount;

  @BsonProperty("guests_amount")
  private final byte guests;
}

package by.vk.bookingsystem.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import by.vk.bookingsystem.dto.home.HomeSetDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "order")
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Order {

  @Id private ObjectId id;

  @BsonProperty(value = "owner_id")
  private String ownerId;

  private LocalDateTime from;
  private LocalDateTime to;
  private BigDecimal cost;
  private boolean confirmed;

  @Field(value = "homes")
  private HomeSetDto homes;

  private byte guests;
}

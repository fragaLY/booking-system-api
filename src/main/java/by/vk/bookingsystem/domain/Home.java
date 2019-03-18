package by.vk.bookingsystem.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "home")
@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class Home {

  @Id private final ObjectId id;
  private final String name;
}

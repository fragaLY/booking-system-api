package by.vk.bookingsystem.domain;

import java.time.LocalDateTime;

import by.vk.bookingsystem.domain.role.Role;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "user")
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode(exclude = "id")
public class User {

  private static final int EXPIRE_IN_ONE_DAY = 86_400;

  @Id private ObjectId id;

  private String firstName;
  private String lastName;
  private Role role;

  @Indexed(unique = true)
  private String email;

  @Indexed(unique = true, expireAfterSeconds = EXPIRE_IN_ONE_DAY)
  private String phone;

  @Field("currency")
  private String currencyCode;

  private String country;
  private String city;
  private LocalDateTime registered;
  private String password;
}

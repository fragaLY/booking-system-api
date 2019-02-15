package by.vk.bookingsystem.domain;

import java.time.LocalDateTime;

import by.vk.bookingsystem.domain.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "id")
public class User {

  private static final int EXPIRE_IN_ONE_DAY = 86_400;

  @Id private final String id;

  private final String firstName;
  private final String lastName;
  private final Role role;

  @Indexed(unique = true)
  private final String email;

  @Indexed(unique = true, expireAfterSeconds = EXPIRE_IN_ONE_DAY)
  private final String phone;

  @Field("currency")
  private final String currencyCode;

  private final String country;
  private final String city;
  private final LocalDateTime registered;
  private final String password;
}

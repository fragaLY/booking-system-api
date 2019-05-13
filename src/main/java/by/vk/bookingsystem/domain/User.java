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
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * The entity of user.
 *
 * @author Vadzim_Kavalkou
 */
@Document(collection = "user")
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode(exclude = "id")
public class User {

  @Id private ObjectId id;

  @TextIndexed private String firstName;
  @TextIndexed private String lastName;
  @Indexed private String email;
  @Indexed private String phone;
  private Role role;

  @Field("currency")
  private String currencyCode;

  private String country;
  private String city;
  private LocalDateTime registered;
  private String password;
}

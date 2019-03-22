package by.vk.bookingsystem.domain.role;

import java.util.Arrays;

import org.springframework.lang.Nullable;

/**
 * The enum with roles that are able in the system
 *
 * @author Vadzim_Kavalkou
 */
public enum Role {
  SURFER,
  USER,
  MANAGER,
  DEVELOPER;

  /**
   * Finds the role by its value.
   *
   * @param value - the value of role.
   * @return {@link Role} or null
   */
  @Nullable
  public static Role getRole(final String value) {
    return Arrays.stream(values())
        .filter(role -> role.name().equalsIgnoreCase(value))
        .findFirst()
        .orElse(null);
  }
}

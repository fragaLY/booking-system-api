package by.vk.bookingsystem.domain.role;

import java.util.Arrays;

public enum Role {
  SURFER,
  USER,
  MANAGER,
  DEVELOPER;

  public static Role getRole(final String value) {
    return Arrays.stream(values())
        .filter(role -> role.name().equals(value))
        .findFirst()
        .orElse(SURFER);
  }
}

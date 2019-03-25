package by.vk.bookingsystem.validator.user;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import by.vk.bookingsystem.domain.role.Role;

/**
 * The validator of {@link Role}
 *
 * @author Vadzim_Kavalkou
 */
public class UserRoleValidator implements ConstraintValidator<UserRole, String> {

  /**
   * Before the validation prepares the data.
   *
   * @param role - {@link Role}
   */
  @Override
  public void initialize(UserRole role) {
    throw new UnsupportedOperationException();
  }

  /**
   * Checks if the role is correct and is in the system.
   *
   * @param value - the value
   * @param context - the context
   * @return {@link Boolean}
   */
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value == null || Role.getRole(value) != null;
  }
}

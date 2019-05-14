package by.vk.bookingsystem.validator.user;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import by.vk.bookingsystem.domain.role.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The validator of {@link Role}
 *
 * @author Vadzim_Kavalkou
 */
public class UserRoleValidator implements ConstraintValidator<UserRole, String> {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleValidator.class);

  /**
   * Before the validation prepares the data.
   *
   * @param role - {@link Role}
   */
  @Override
  public void initialize(UserRole role) {
    LOGGER.debug("UserRoleValidator had been initialized");
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
    return value != null && Role.getRole(value) != null;
  }
}

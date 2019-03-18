package by.vk.bookingsystem.validator.user;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import by.vk.bookingsystem.domain.role.Role;

public class UserRoleValidator implements ConstraintValidator<UserRole, String> {

  @Override
  public void initialize(UserRole role) {}

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value == null || Role.getRole(value) != null;
  }
}

package by.vk.bookingsystem.validator.user;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * The annotation for user's role validation
 *
 * @author Vadzim_Kavalkou
 */
@Documented
@Constraint(validatedBy = UserRoleValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserRole {

  String message() default "Unknown user role";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

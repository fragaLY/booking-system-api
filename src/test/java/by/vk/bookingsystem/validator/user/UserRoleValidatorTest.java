package by.vk.bookingsystem.validator.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserRoleValidatorTest {

  private UserRoleValidator userRoleValidator;

  @Before
  public void setUp() {
    userRoleValidator = new UserRoleValidator();
  }

  @Test
  public void isValid_whenValueNull_returnFalse() {
    assertFalse(userRoleValidator.isValid(null, null));
  }

  @Test
  public void isValid_whenValueNotNullAndRoleNotFound_returnFalse() {
    assertFalse(userRoleValidator.isValid("", null));
  }

  @Test
  public void isValid_whenValueNotNullAndRoleFound_returnTrue() {
    assertTrue(userRoleValidator.isValid("DEVELOPER", null));
  }
}

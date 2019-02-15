package by.vk.bookingsystem.dao.impl;

import java.time.LocalDateTime;
import java.util.Set;

import by.vk.bookingsystem.dao.UserDao;
import by.vk.bookingsystem.domain.User;
import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Repository;

@Repository
public abstract class UserDaoImpl implements UserDao {

  @Override
  public User getUsersByEmail(String email) {
    return User.builder().build();
  }

  @Override
  public Set<User> findAllByRegisteredBetween(LocalDateTime from, LocalDateTime to) {
    return ImmutableSet.of(User.builder().build());
  }
}

package ru.practicum.shareit.user;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface UserRepository {
    Optional<User> getUserById(Long id);

    Collection<User> getAllUser();

    User save(User user);

    User update(long id, User user);

    void delete(long id);

    Set<String> getAllEmail();
}

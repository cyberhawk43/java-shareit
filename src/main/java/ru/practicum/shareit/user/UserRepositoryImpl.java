package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private final Set<String> emails = new HashSet<>();
    private long id = 1;

    @Override
    public Optional<User> getUserById(Long id) {
        return users.keySet().stream().filter(i -> Objects.equals(i, id)).findAny().map(users::get);

    }

    @Override
    public Collection<User> getAllUser() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User save(User user) {
        user.setId(createId());
        emails.add(user.getEmail());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(long id, User user) {
        user.setId(id);
        users.put(id, user);
        return user;
    }

    @Override
    public void delete(long id) {
        emails.remove(users.get(id).getEmail());
        users.remove(id);
    }

    @Override
    public Set<String> getAllEmail() {
        return emails;
    }

    private long createId() {
        return id++;
    }
}

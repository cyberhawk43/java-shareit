package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.AllReadyException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto getUserById(long id) {
        User user = userRepository.getUserById(id).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return toUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.getAllUser().stream().map(this::toUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        //проверка на уникальный email
        if (userRepository.getAllEmail()
                .stream()
                .anyMatch(e -> Objects.equals(e, userDto.getEmail()))) {
            throw new AllReadyException("Такой email уже зарегестрирован");
        }
        return toUserDto(userRepository.save(toUser(userDto)));
    }

    @Override
    public UserDto updateUser(long id, UserDto userDto) {
        //проверка на уникальный email
        if (userRepository.getAllEmail()
                .stream()
                .anyMatch(e -> Objects.equals(e, userDto.getEmail()))) {
            throw new AllReadyException("Такой email уже зарегестрирован");
        }
        User user = userRepository.getUserById(id).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            userRepository.getAllEmail().remove(user.getEmail());
            user.setEmail(userDto.getEmail());
        }
        return toUserDto(userRepository.update(id, user));
    }

    @Override
    public void deleteUser(long id) {
        User user = userRepository.getUserById(id).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        userRepository.delete(id);
    }

    private UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    private User toUser(UserDto userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getEmail());
    }
}

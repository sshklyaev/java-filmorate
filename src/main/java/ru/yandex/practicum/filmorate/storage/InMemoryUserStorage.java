package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class InMemoryUserStorage implements UserStorage {
    private Map<Long, User> users = new HashMap<>();
    private Long currentId = Long.valueOf(1);

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(User user) {
        if (isValidUser(user)) {
            user.setId(currentId++ );
            users.put(user.getId(), user);
        }
        return user;
    }

    @Override
    public User update(User user) {
        if (user.getId() == null) {
            throw new ValidationException("Передан пустой аргумент!");
        }
        if (!users.containsKey(user.getId())) {
            throw new UserNotFoundException("Пользователь с ID=" + user.getId() + " не найден!");
        }
        if (isValidUser(user)) {
            users.put(user.getId(), user);
        }
        return user;
    }

    @Override
    public User getUserById(Long userId) {
        if (!users.containsKey(userId)) {
            throw new UserNotFoundException("Пользователь с ID=" + userId + " не найден!");
        }
        return users.get(userId);
    }

    @Override
    public User delete(Long userId) {
        if (userId == null) {
            throw new ValidationException("Передан пустой аргумент!");
        }
        if (!users.containsKey(userId)) {
            throw new UserNotFoundException("Пользователь с ID=" + userId + " не найден!");
        }

        for (User user : users.values()) {
            user.getFriends().remove(userId);
        }
        return users.remove(userId);
    }

    private boolean isValidUser(User user) {
        if (!user.getEmail().contains("@")) {
            throw new ValidationException("Некорректный e-mail пользователя: " + user.getEmail());
        }
        if ((user.getLogin().isEmpty()) || (user.getLogin().contains(" "))) {
            throw new ValidationException("Некорректный логин пользователя: " + user.getLogin());
        }

        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Некорректная дата рождения пользователя: " + user.getBirthday());
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return true;
    }
}

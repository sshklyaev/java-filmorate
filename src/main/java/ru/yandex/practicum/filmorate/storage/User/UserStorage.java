package ru.yandex.practicum.filmorate.storage.User;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    List<User> getUsers();

    User create(User user);

    User update(User user);

    User getUserById(Long userId);
}

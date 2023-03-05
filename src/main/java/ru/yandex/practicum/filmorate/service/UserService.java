package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(Long userId, Long friendId) {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        userStorage.getUserById(userId).getFriends().remove(friendId);
        userStorage.getUserById(friendId).getFriends().remove(userId);
    }

    public List<User> getFriends(Long userId) {
        return userStorage.getUserById(userId).getFriends()
                .stream()
                .map(friendId -> userStorage.getUserById(friendId))
                .collect(Collectors.toList());
    }


    public List<User> getCommonFriends(Long firstUserId, Long secondUserId) {
        List<User> commonFriends = new ArrayList<>();
        User firstUser = userStorage.getUserById(firstUserId);
        User secondUser = userStorage.getUserById(secondUserId);
        if (firstUser.getFriends() != null && secondUser.getFriends() != null) {
            for (Long id : firstUser.getFriends()) {
                if (secondUser.getFriends().contains(id)) {
                    commonFriends.add(userStorage.getUserById(id));
                }
            }
        }
        return commonFriends;
    }


    public List<User> getUsers() {
        return userStorage.getUsers();
    }


    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User user) {
        return userStorage.update(user);
    }


    public User getUserById(Long userId) {
        return userStorage.getUserById(userId);
    }


    public User delete(Long userId) {
        return userStorage.delete(userId);
    }
}

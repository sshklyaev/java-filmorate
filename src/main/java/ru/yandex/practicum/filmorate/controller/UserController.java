package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.UserService;
import storage.UserStorage;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private UserStorage userStorage;
    private UserService userService;

    @Autowired
    public UserController(UserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userStorage.getUserById(id);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable Long id) {
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.getCommonFriends(id, otherId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.deleteFriend(id, friendId);
    }


    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Получен POST-запрос к эндпоинту: '/users' на добавление пользователя");
        user = userStorage.create(user);
        return user;
    }


    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Получен PUT-запрос к эндпоинту: '/users' на обновление пользователя с ID={}", user.getId());
        user = userStorage.update(user);
        return user;
    }

    @DeleteMapping("/{id}")
    public User delete(@PathVariable Long id) {
        log.info("Получен DELETE-запрос к эндпоинту: '/users' на удаление пользователя с ID={}", id);
        return userStorage.delete(id);
    }
}
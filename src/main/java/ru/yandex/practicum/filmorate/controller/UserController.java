package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private Map<Integer, User> users = new HashMap<>();
    private Integer currentId = 1;


    @GetMapping
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }


    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Получен POST-запрос к эндпоинту: '/users' на добавление пользователя с ID={}", currentId + 1);
        if (isValidUser(user)) {
            user.setId(currentId++);
            users.put(user.getId(), user);
        }
        return user;
    }


    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Получен PUT-запрос к эндпоинту: '/users' на обновление пользователя с ID={}", user.getId());
        if (user.getId() == null) {
            throw new ValidationException("Передан пустой аргумент!");
        }
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Пользователь с ID=" + user.getId() + " не найден!");
        }
        if (isValidUser(user)) {
            users.put(user.getId(), user);
        }
        return user;
    }

    private boolean isValidUser(User user) {
        if ((user.getLogin().isEmpty()) || (user.getLogin().contains(" "))) {
            throw new ValidationException("Некорректный логин пользователя: " + user.getLogin());
        }
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return true;
    }

}

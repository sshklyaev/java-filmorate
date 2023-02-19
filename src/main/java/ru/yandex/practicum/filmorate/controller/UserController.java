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
public class UserController {
    private Map<Integer, User> users;
    private Integer currentId;

    public UserController() {
        currentId = 0;
        users = new HashMap<>();
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }


    @PostMapping(value = "/users")
    public User create(@Valid @RequestBody User user) {
        log.info("Получен POST-запрос к эндпоинту: '/users' на добавление пользователя с ID={}", currentId + 1);
        user.setId(++currentId);
        users.put(user.getId(), user);

        return user;
    }

    @ResponseBody
    @PutMapping(value = "/users")
    public User update(@Valid @RequestBody User user) {
        log.info("Получен PUT-запрос к эндпоинту: '/users' на обновление пользователя с ID={}", user.getId());
        if (isContainsUser(user.getId()) == true) {
            users.put(user.getId(), user);
            currentId++;
        }
        return user;
    }

    private boolean isContainsUser(int id) {
        return users.containsKey(id);
    }
}

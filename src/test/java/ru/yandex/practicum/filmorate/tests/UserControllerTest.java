package ru.yandex.practicum.filmorate.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {
    private User user;
    private UserController userController;
    private UserStorage userStorage;

    @BeforeEach
    public void beforeEach() {
        InMemoryUserStorage userStorage = new InMemoryUserStorage();
        UserController userController = new UserController(userStorage, new UserService(userStorage));
        User user = User.builder()
                .name("Daniil")
                .login("Willixz")
                .email("1@ya.ru")
                .birthday(LocalDate.of(1980, 12, 23))
                .build();
    }

    @Test
    public void shouldNoAddUserWhenUserLoginIsContainsSpaces() {
        user.setLogin("Daniil Shklyaev");
        assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пустым");
    }

    @Test
    public void shouldAddUserWhenAllAttributeCorrect() {
        User user1 = userController.create(user);
        assertEquals(user, user1, "Переданный и полученный пользователь должны совпадать");
        assertEquals(1, userController.getUsers().size(), "В списке должен быть один пользователь");
    }

}


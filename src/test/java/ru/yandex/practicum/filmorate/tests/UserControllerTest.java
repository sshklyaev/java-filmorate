package ru.yandex.practicum.filmorate.tests;

import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UserControllerTest {
    private User user;
    private UserController userController;

    @BeforeEach
    public void beforeEach() {
        userController = new UserController();
        user = User.builder()
                .name("MyName")
                .login("Willixz")
                .email("1@ya.ru")
                .birthday(LocalDate.of(2000, 12, 12))
                .build();
    }


    @Test
    public void shouldAddUserWhenAllAttributeCorrect() {
        User user1 = userController.create(user);
        assertEquals(user, user1, "Переданный и полученный пользователь должны совпадать");
        assertEquals(1, userController.getUsers().size(), "В списке должен быть один пользователь");
    }


    @Test
    public void shouldNoAddUserWhenUserEmailIsEmpty() {
        user.setEmail("");
        assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пустым");
    }


    @Test
    public void shouldNoAddUserWhenUserEmailIsNotContainsCommercialAt() {
        user.setEmail("notemail.ru");
        assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пустым");
    }


    @Test
    public void shouldNoAddUserWhenUserLoginIsEmpty() {
        user.setLogin("");
        assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пустым");
    }


    @Test
    public void shouldNoAddUserWhenUserLoginIsContainsSpaces() {
        user.setLogin("Max Power");
        assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пустым");
    }


    @Test
    public void shouldAddUserWhenUserNameIsEmpty() {
        user.setName("");
        User user1 = userController.create(user);
        assertTrue(user1.getName().equals(user.getLogin()),
                "Имя и логин пользователя должны совпадать");
        assertEquals(1, userController.getUsers().size(), "В списке должен быть один пользователь");
    }


    @Test
    public void shouldAddUserWhenUserBirthdayInFuture() {
        user.setBirthday(LocalDate.now().plusDays(1));
        assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пустым");
    }
}


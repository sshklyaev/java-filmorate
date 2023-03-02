package ru.yandex.practicum.filmorate.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.controller.UserController;


import ru.yandex.practicum.filmorate.model.User;


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
                .name("Daniil")
                .login("willixz")
                .email("yandex@ya.ru")
                .birthday(LocalDate.of(2000, 07, 23))
                .build();
    }

    @Test
    public void shouldNoAddUserWhenUserLoginIsContainsSpaces() {
        user.setLogin("Daniil Shklyaev");
        assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пустым");
    }

}
package tests;

import controller.UserController;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserControllerTest {
    private User user;
    private UserController userController;

    @BeforeEach
    public void beforeEach() {
        userController = new UserController();
        user = new User();
        user.setName("Daniil");
        user.setLogin("Willixz");
        user.setEmail("willixz@ya.ru");
        user.setBirthday(LocalDate.of(2004, 7, 12));
    }

    @Test
    public void shouldAddUserWhenAllAttributeCorrect() {
        Object user1 = userController.saveUser(user);
        assertEquals(user, user1, "Переданный и полученный пользователь должны совпадать");
        assertEquals(1, userController.getUsers().size(), "В списке должен быть один пользователь");
    }


    @Test
    public void shouldNoAddUserWhenUserIsNull() {
        user = null;
        Object object = userController.saveUser(user);
        assertTrue(object.toString().contains("BAD_REQUEST"), "Должен быть получен 400 ответ от сервера");
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пустым");
    }


    @Test
    public void shouldNoAddUserWhenUserEmailIsNull() {
        user.setEmail(null);
        Object object = userController.saveUser(user);
        assertTrue(object.toString().contains("BAD_REQUEST"), "Должен быть получен 400 ответ от сервера");
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пустым");
    }


    @Test
    public void shouldNoAddUserWhenUserEmailIsEmpty() {
        user.setEmail("");
        Object object = userController.saveUser(user);
        assertTrue(object.toString().contains("BAD_REQUEST"), "Должен быть получен 400 ответ от сервера");
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пустым");
    }


    @Test
    public void shouldNoAddUserWhenUserEmailIsNotContainsCommercialAt() {
        user.setEmail("willixz.ru");
        Object object = userController.saveUser(user);
        assertTrue(object.toString().contains("BAD_REQUEST"), "Должен быть получен 400 ответ от сервера");
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пустым");
    }


    @Test
    public void shouldNoAddUserWhenUserLoginIsNull() {
        user.setLogin(null);
        Object object = userController.saveUser(user);
        assertTrue(object.toString().contains("BAD_REQUEST"), "Должен быть получен 400 ответ от сервера");
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пустым");
    }


    @Test
    public void shouldNoAddUserWhenUserLoginIsEmpty() {
        user.setLogin("");
        Object object = userController.saveUser(user);
        assertTrue(object.toString().contains("BAD_REQUEST"), "Должен быть получен 400 ответ от сервера");
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пустым");
    }



    @Test
    public void shouldAddUserWhenUserNameIsEmpty() {
        user.setName("");
        Object object = userController.saveUser(user);
        assertTrue(((User) object).getName().equals(user.getLogin()),
                "Имя и логин пользователя должны совпадать");
        assertEquals(1, userController.getUsers().size(), "В списке должен быть один пользователь");
    }


    @Test
    public void shouldAddUserWhenUserNameIsNull() {
        user.setName(null);
        Object object = userController.saveUser(user);
        assertTrue(object.toString().contains("BAD_REQUEST"), "Должен быть получен 400 ответ от сервера");
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пустым");
    }


    @Test
    public void shouldAddUserWhenUserBirthdayInFuture() {
        user.setBirthday(LocalDate.now().plusDays(1));
        Object object = userController.saveUser(user);
        assertTrue(object.toString().contains("BAD_REQUEST"), "Должен быть получен 400 ответ от сервера");
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пустым");
    }


    @Test
    public void shouldAddUserWhenUserBirthdayIsNull() {
        user.setBirthday(null);
        Object object = userController.saveUser(user);
        assertTrue(object.toString().contains("BAD_REQUEST"), "Должен быть получен 400 ответ от сервера");
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пустым");
    }
}


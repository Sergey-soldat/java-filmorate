import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {
    private final UserController userController = new UserController();

    @Test
    public void testUserValidateRightCreation() {
        User user = new User(3, "yandex@ya.ru", "yandex", "Test", LocalDate.of(2000, 1, 1));
        assertEquals(user, userController.create(user));
    }

    @Test
    public void testUserWithoutName() {
        User user = new User(33, "yandex@ya.ru", "", "yandex", LocalDate.of(2000, 1, 1));
        userController.create(user);
        assertEquals(user.getName(), user.getLogin());
    }

    @Test
    public void spaceInLogin() {
        User user = new User(3, "yandex@ya.ru", "yandex", "Tes t", LocalDate.of(2000, 1, 1));
        assertThrows(ValidationException.class, () -> userController.create(user));
    }

    @Test
    public void emailTest() {
        User user = new User(3, "yandexya.ru", "yandex", "Test", LocalDate.of(2000, 1, 1));
        assertThrows(ValidationException.class, () -> userController.create(user));
    }

    @Test
    public void nameLoginBlank() {
        User user = new User(3, "yandex@ya.ru", " ", "Test", LocalDate.of(2000, 1, 1));
        userController.create(user);
        assertEquals(user.getName(), user.getLogin());
    }

    @Test
    public void nameLoginNull() {
        User user = new User(3, "yandex@ya.ru", null, "Test", LocalDate.of(2000, 1, 1));
        userController.create(user);
        assertEquals(user.getName(), user.getLogin());
    }

    @Test
    public void containsKeyId() {
        User user = new User(3, "yandex@ya.ru", "Имя1", "Test", LocalDate.of(2000, 1, 1));
        User user2 = new User(3, "yandex@ya.ru", "Имя2", "Test", LocalDate.of(2000, 1, 1));
        userController.create(user);
        userController.create(user2);
        assertEquals("Имя2", user2.getName());
    }

    @Test
    public void loginBlank() {
        User user = new User(33, "yandex@ya.ru", "Имя", " ", LocalDate.of(2000, 1, 1));
        assertThrows(ValidationException.class, () -> userController.create(user));
    }

    @Test
    public void loginNull() {
        User user = new User(33, "yandex@ya.ru", "Имя", null, LocalDate.of(2000, 1, 1));
        assertThrows(ValidationException.class, () -> userController.create(user));
    }

    @Test
    public void emailBlank() {
        User user = new User(33, " ", "Имя", "yandex", LocalDate.of(2000, 1, 1));
        assertThrows(ValidationException.class, () -> userController.create(user));
    }

    @Test
    public void birthdayAfterNow() {
        User user = new User(33, "yandex@ya.ru", "Имя", "yandex", LocalDate.of(2200, 1, 1));
        assertThrows(ValidationException.class, () -> userController.create(user));
    }
}

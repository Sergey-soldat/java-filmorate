import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {
    private final InMemoryUserStorage userStorage = new InMemoryUserStorage();
    private final UserService userService = new UserService(userStorage);

    @Test
    public void testUserValidateRightCreation() {
        User user = new User(3, "yandex@ya.ru", "yandex", "Test",
                LocalDate.of(2000, 1, 1), false, new HashSet<>());
        assertEquals(user, userService.createUser(user));
    }

    @Test
    public void testUserWithoutName() {
        User user = new User(33, "yandex@ya.ru", "yandex", "",
                LocalDate.of(2000, 1, 1), false, new HashSet<>());
        userService.createUser(user);
        assertEquals(user.getName(), user.getLogin());
    }

    @Test
    public void spaceInLogin() {
        User user = new User(3, "yandex@ya.ru", "yande x", "Test",
                LocalDate.of(2000, 1, 1), false,  new HashSet<>());
        assertThrows(ValidationException.class, () -> userService.createUser(user));
    }

    @Test
    public void containsKeyId() {
        User user = new User(3, "yandex@ya.ru", "yandex", "Name1",
                LocalDate.of(2000, 1, 1), false,  new HashSet<>());
        User user2 = new User(3, "yandex@ya.ru", "yandex", "Name2",
                LocalDate.of(2000, 1, 1), false,  new HashSet<>());
        userService.createUser(user);
        userService.createUser(user2);
        assertEquals("Name2", user2.getName());
    }
}

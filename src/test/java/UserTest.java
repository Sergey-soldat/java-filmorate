import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {
    private final UserStorage userStorage = new InMemoryUserStorage();

    @Test
    public void testUserValidateRightCreation() {
        User user = new User(3, "yandex@ya.ru", "yandex", "Test",
                LocalDate.of(2000, 1, 1), new HashSet<>());
        assertEquals(user, userStorage.createUser(user));
    }

    @Test
    public void testUserWithoutName() {
        User user = new User(33, "yandex@ya.ru", "yandex", "",
                LocalDate.of(2000, 1, 1), new HashSet<>());
        userStorage.createUser(user);
        assertEquals(user.getName(), user.getLogin());
    }

    @Test
    public void spaceInLogin() {
        User user = new User(3, "yandex@ya.ru", "yande x", "Test",
                LocalDate.of(2000, 1, 1), new HashSet<>());
        assertThrows(ValidationException.class, () -> userStorage.createUser(user));
    }

    @Test
    public void containsKeyId() {
        User user = new User(3, "yandex@ya.ru", "yandex", "Name1",
                LocalDate.of(2000, 1, 1), new HashSet<>());
        User user2 = new User(3, "yandex@ya.ru", "yandex", "Name2",
                LocalDate.of(2000, 1, 1), new HashSet<>());
        userStorage.createUser(user);
        userStorage.createUser(user2);
        assertEquals("Name2", user2.getName());
    }
}

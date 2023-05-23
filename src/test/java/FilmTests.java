import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmTests {
    private final FilmController filmController = new FilmController();

    @Test
    public void testFilmRightCreation() {
        Film film = new Film(12, "Film1", "Корректная дата релиза",
                LocalDate.of(2222, 12, 2), 22);
        assertEquals(film, filmController.create(film));
    }

    @Test
    public void testFilmInCorrectDateRelease() {
        Film film = new Film(2, "Film2", "Некорректная дата релиза",
                LocalDate.of(1000, 11, 1), 11);
        assertThrows(ValidationException.class, () -> filmController.create(film));
    }

    @Test
    public void minusDuration() {
        Film film = new Film(12, "Film1", "Корректная дата релиза",
                LocalDate.of(2222, 12, 2), -22);
        assertThrows(ValidationException.class, () -> filmController.create(film));
    }

    @Test
    public void zeroDuration() {
        Film film = new Film(12, "Film1", "Корректная дата релиза",
                LocalDate.of(2222, 12, 2), 0);
        assertThrows(ValidationException.class, () -> filmController.create(film));
    }

    @Test
    public void nameBlank() {
        Film film = new Film(12, " ", "Корректная дата релиза",
                LocalDate.of(2222, 12, 2), 22);
        assertThrows(ValidationException.class, () -> filmController.create(film));
    }

    @Test
    public void descriptionBlank() {
        Film film = new Film(12, "Имя", " ",
                LocalDate.of(2222, 12, 2), 22);
        assertThrows(ValidationException.class, () -> filmController.create(film));
    }

    @Test
    public void descriptionNull() {
        Film film = new Film(12, "Имя", null,
                LocalDate.of(2222, 12, 2), 22);
        assertThrows(ValidationException.class, () -> filmController.create(film));
    }
}

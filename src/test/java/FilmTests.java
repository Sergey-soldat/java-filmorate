//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.web.servlet.MockMvc;
//import ru.yandex.practicum.filmorate.dao.dao.FilmDao;
//import ru.yandex.practicum.filmorate.dao.dao.LikeDao;
//import ru.yandex.practicum.filmorate.dao.db.FilmDb;
//import ru.yandex.practicum.filmorate.dao.db.LikeDb;
//import ru.yandex.practicum.filmorate.exception.ValidationException;
//import ru.yandex.practicum.filmorate.model.Film;
//import ru.yandex.practicum.filmorate.service.FilmService;
//import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmDao;
//import ru.yandex.practicum.filmorate.storage.user.InMemoryUserDao;
//
//import java.time.LocalDate;
//import java.util.HashSet;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//public class FilmTests {
//    private final InMemoryFilmDao filmStorage = new InMemoryFilmDao();
//    private final InMemoryUserDao userStorage = new InMemoryUserDao();
//    private final FilmDao filmDao = new FilmDb();
//
//    private final FilmService filmService = new FilmService(filmStorage, userStorage);
//
//    @Autowired
//    private MockMvc mockMvc;
//    private final String urlTemplate = "/films";
//
//    @Test
//    public void testFilmRightCreation() {
//        Film film = new Film(12, "Film1", "Корректная дата релиза",
//                LocalDate.of(2222, 12, 2),  22, new HashSet<>(), null, new HashSet<>());
//        filmService.createFilm(film);
//        assertEquals(film, filmService.getFilmById(film.getId()));
//    }
//
//    @Test
//    public void testFilmInCorrectDateRelease() {
//        Film film = new Film(2, "Film2", "Некорректная дата релиза",
//                LocalDate.of(1000, 11, 1), 11, "genre", "MPA", new HashSet<>());
//        assertThrows(ValidationException.class, () -> filmService.createFilm(film));
//    }
//}

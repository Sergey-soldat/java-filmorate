import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.dao.db.FilmDb;
import ru.yandex.practicum.filmorate.dao.db.GenreDb;
import ru.yandex.practicum.filmorate.dao.db.MpaDb;
import ru.yandex.practicum.filmorate.dao.db.UserDb;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(scripts = {"/schema.sql", "/filmTestData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/dropAll.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class FilmoRateApplicationTests {
    private final UserDb userStorage;
    private final FilmDb filmStorage;
    private final MpaDb mpaStorage;

    private final GenreDb genreStorage;


    @Test
    @DisplayName("Получение пользователя по id")
    public void testFindUserById() {

        User user = userStorage.getUser(1);

        assertThat(user).hasFieldOrPropertyWithValue("id", 1);
        assertThat(user).hasFieldOrPropertyWithValue("email", "em@mail.ru");
    }

    @Test
    @DisplayName("Получение всех пользователей")
    public void testFindAllUsers() {

        List<User> users = (List<User>) userStorage.getUsers();

        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Сохранине пользователя в базе данных c последующим удалением")
    public void testCreateAndDeleteUser() {
        User user = new User("email@yandex.ru", "user3", "name", LocalDate.of(1900, 2, 1));
        userStorage.createUser(user);
        List<User> users = (List<User>) userStorage.getUsers();

        assertThat(users.size()).isEqualTo(3);
        assertThat(users.get(2)).hasFieldOrPropertyWithValue("email", "email@yandex.ru");

        userStorage.deleteUser(user.getId());

        List<User> users2 = (List<User>) userStorage.getUsers();

        assertThat(users2.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Обновление данных пользователя")
    public void testUpdateUser() {
        User user = new User("newemail@yandex.ru", "newUser", "", LocalDate.of(1901, 2, 1));
        user.setId(1);
        userStorage.updateUser(user);
        List<User> users = (List<User>) userStorage.getUsers();

        assertThat(users.size()).isEqualTo(2);
        assertThat(users.get(0)).hasFieldOrPropertyWithValue("email", "newemail@yandex.ru");
        assertThat(users.get(0)).hasFieldOrPropertyWithValue("login", "newUser");
        assertThat(users.get(0)).hasFieldOrPropertyWithValue("name", "newUser");
    }

    @Test
    @DisplayName("Получение фильма по id")
    public void testFindFilmById() {

        Film film = filmStorage.getFilmById(1);

        assertThat(film).hasFieldOrPropertyWithValue("id", 1);
        assertThat(film).hasFieldOrPropertyWithValue("name", "FILM");
    }

    @Test
    @DisplayName("Получение всех фильмов")
    public void testFindAllFilms() {

        List<Film> films = (List<Film>) filmStorage.getFilms();

        assertThat(films.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Сохранине фильма в базе данных c последующим удалением")
    public void testCreateAndDeleteFilm() {
        Film film = new Film("newFilm", "des", LocalDate.of(2002, 3, 4), 100);
        film.setMpa(new Mpa(1,"G"));
        filmStorage.createFilm(film);
        List<Film> films = (List<Film>) filmStorage.getFilms();

        assertThat(films.size()).isEqualTo(4);
        assertThat(films.get(3)).hasFieldOrPropertyWithValue("name", "newFilm");

        filmStorage.deleteFilm(film);

        List<Film> films2 = (List<Film>) filmStorage.getFilms();

        assertThat(films2.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Обновление данных фильма")
    public void testUpdateFilm() {
        Film film = new Film("newFilm", "newDesc", LocalDate.of(2002, 3, 4), 100);
        film.setId(1);
        film.setMpa(new Mpa(1,"G"));
        filmStorage.updateFilm(film);
        List<Film> films = (List<Film>) filmStorage.getFilms();

        assertThat(films.size()).isEqualTo(3);
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("name", "newFilm");
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("description", "newDesc");
    }

    @Test
    @DisplayName("Получение рейтинга по id")
    public void testFindMpaById() {
        Mpa mpa = mpaStorage.getById(1);

        assertThat(mpa).hasFieldOrPropertyWithValue("name", "G");
    }

    @Test
    @DisplayName("Получение списка рейтингов")
    public void testFindAllMpa() {
        List<Mpa> mpaList = (List<Mpa>) mpaStorage.findAll();

        assertThat(mpaList.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("Получение жанра по id")
    public void testFindGenreById() {
        Genre genre = genreStorage.getById(1);

        assertThat(genre).hasFieldOrPropertyWithValue("name", "Комедия");
    }

    @Test
    @DisplayName("Получение списка всех жанров")
    public void testFindAllGenres() {
        List<Genre> genreList = (List<Genre>) genreStorage.findAll();

        assertThat(genreList.size()).isEqualTo(6);
    }
}
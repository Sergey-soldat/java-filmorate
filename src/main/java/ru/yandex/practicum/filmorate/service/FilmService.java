package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage, InMemoryUserStorage inMemoryUserStorage) {
        this.filmStorage = inMemoryFilmStorage;
        this.userStorage = inMemoryUserStorage;
    }

    private void checkUser(int userId) {
        if (!userStorage.getUsers().containsKey(userId)) {
            log.debug("Пользователь  с id = " + userId + " не найден");
            throw new NotFoundException("Пользователь  с айди = " + userId + " не найден");
        }
    }

    private void checkFilm(Integer filmId, boolean isValid) {
        if (isValid) {
            if (!filmStorage.getFilms().containsKey(filmId)) {
                log.debug("Фильм  с id = " + filmId + " не найден");
                throw new NotFoundException("Фильм с айди =" + filmId + " не найден");
            }
        } else if (filmId != 0 && /*getFilmById(filmId) != null*/
                filmStorage.getFilms().containsKey(filmId)) {
            log.debug("Фильм с айди =" + filmId + " уже создан");
            throw new AlreadyExistException("Фильм с айди =" + filmId + " уже создан");
        }
    }

    public List<Film> getAll() {
        return filmStorage.getAllFilms();
    }

    public Film createFilm(Film film) {
        checkFilm(film.getId(), false);
        return filmStorage.createFilm(film);
    }

    public Film update(Film film) {
        if (film.getId() <= 0) {
            throw new ValidationException("Нельзя обновить Пользователя с айди =" + film.getId());
        }
        checkFilm(film.getId(), true);
        return filmStorage.updateFilm(film);
    }

    public void addLike(Integer filmId, int userId) {
        checkFilm(filmId, true);
        checkUser(userId);
        filmStorage.addLike(filmId, userId);
    }

    public void deleteLike(Integer filmId, int userId) {
        checkFilm(filmId, true);
        checkUser(userId);
        filmStorage.getFilms().get(filmId).getUserIdLikes().remove(userId);
    }

    public List<Film> getMostPopularFilms(Integer id) {
        return filmStorage.getPopularFilms(id);
    }

    public Film getFilmById(Integer filmId) {
        checkFilm(filmId, true);
        return filmStorage.getFilmById(filmId);
    }
}

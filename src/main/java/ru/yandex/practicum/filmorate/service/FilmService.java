package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.dao.LikeDao;
import ru.yandex.practicum.filmorate.dao.db.FilmDb;
import ru.yandex.practicum.filmorate.dao.db.LikeDb;
import ru.yandex.practicum.filmorate.dao.db.UserDb;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.dao.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.dao.UserDao;

import java.util.Collection;


@Slf4j
@Service
public class FilmService {
    private final FilmDao filmStorage;
    private final UserDao userStorage;
    private final LikeDao likeStorage;

    public FilmService(FilmDb filmStorage, UserDb userStorage, LikeDb likeStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.likeStorage = likeStorage;
    }

    public Film getFilmById(Integer id) {
        filmStorage.validationId(id);
        return filmStorage.getFilmById(id);
    }

    public Collection<Film> findAllTopFilms(Integer count) {
        return filmStorage.findAllTopFilms(count);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        filmStorage.validationId(filmId);
        userStorage.validationId(userId);
        likeStorage.deleteLike(filmId, userId);
    }

    public void addLike(Integer filmId, Integer userId) {
        userStorage.validationId(userId);
        filmStorage.validationId(filmId);
        likeStorage.addLike(filmId, userId);
    }

    public Collection<Film> findAll() {
        log.debug("Текущее количество фильмов: {}", filmStorage.getFilms().size());
        return filmStorage.getFilms();
    }

    public Film createFilm(Film film) {
        filmStorage.createFilm(film);
        return film;
    }

    public Film updateFilm(Film film) {
        if (filmStorage.updateFilm(film)) {
            return film;
        }
        log.debug("Фильм с id: {} не найден", film.getId());
        return null;
    }
}

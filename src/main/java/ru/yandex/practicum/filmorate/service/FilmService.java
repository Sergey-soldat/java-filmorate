package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.dao.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.dao.GenreDao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmDao filmDao;
    private final GenreDao genreDao;

    public List<Film> getFilms() {
        return genreDao.setGenresInFilm(filmDao.getFilms());
    }

    public Film addFilm(Film film) {
        validate(film);
        return genreDao.setGenreInFilm(filmDao.addFilm(film));
    }

    public Film updateFilm(Film film) {
        validate(film);
        if (filmDao.checkFilmExistInBd(film.getId())) {
            genreDao.deleteFilmGenre(film);
            return genreDao.setGenreInFilm(filmDao.updateFilm(film));
        } else {
            throw new NotFoundException("Фильм не найден");
        }
    }

    public Film getFilmById(int filmId) {
        Film film = filmDao.getFilmById(filmId);
        if (film == null) {
            throw new NotFoundException("Фильм не найден");
        }
        return genreDao.setGenreInFilm(film);

    }

    public Film addLike(int filmId, int userId) {
        if (filmDao.checkFilmExistInBd(filmId)) {
            filmDao.addLike(userId, filmId);
            log.info("Добавлен лайк от - {} фильму - {}", userId, filmId);
            return genreDao.setGenreInFilm(filmDao.getFilmById(filmId));
        } else {
            throw new NotFoundException("Фильм не найден");
        }
    }

    public Film deleteLike(int filmId, int userId) {
        if (checkFilmAndLikeInExistInDb(filmId, userId)) {
            filmDao.deleteLike(userId, filmId);
            log.info("Удален лайк - {} у фильму - {}", userId, filmId);
            return genreDao.setGenreInFilm(filmDao.getFilmById(filmId));
        } else if (!filmDao.checkFilmExistInBd(filmId)) {
            throw new NotFoundException("Фильм не найден");
        } else {
            throw new NotFoundException("Пользователь не найден");
        }
    }

    public List<Film> getFamousFilms(Integer count) {
        if (count != null) {
            return getFilms().stream()
                    .sorted((o1, o2) -> o2.getLikes().size() - o1.getLikes().size())
                    .limit(count)
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    private boolean checkFilmAndLikeInExistInDb(int id, int userId) {
        return filmDao.checkFilmExistInBd(id) && filmDao.checkFilmExistInBd(userId);
    }

    private void validate(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)) ||
                film.getDescription().length() > 200 ||
                film.getDuration() <= 0) {
            throw new ValidationException();
        }
    }
}

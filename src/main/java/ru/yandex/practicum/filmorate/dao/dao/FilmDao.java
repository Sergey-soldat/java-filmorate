package ru.yandex.practicum.filmorate.dao.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface FilmDao {

//    List<Film> getAllFilms();

    void createFilm(Film film);

    void deleteFilm(Film film);

    boolean updateFilm(Film film);

//    void addLike(int filmId, int userId);
//
//    void deleteLike(int filmId, int userId);

    Film getFilmById(Integer filmId);

    Collection<Film> getFilms();

    void validationId(Integer id);
}

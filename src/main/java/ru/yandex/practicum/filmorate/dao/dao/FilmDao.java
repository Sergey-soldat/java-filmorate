package ru.yandex.practicum.filmorate.dao.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmDao {

    void createFilm(Film film);

    void deleteFilm(Film film);

    boolean updateFilm(Film film);

    Film getFilmById(Integer filmId);

    Collection<Film> getFilms();

    Collection<Integer> findAllTopFilms(Integer count);

    void validationId(Integer id);
}

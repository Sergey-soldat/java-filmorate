package ru.yandex.practicum.filmorate.dao.dao;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreDao {

    Genre getGenreById(int id);

    List<Genre> getAllGenre();

    void createFilmGenre(Film film);

    void deleteFilmGenre(Film film);

    List<Film> setGenresInFilm(List<Film> films);

    Film setGenreInFilm(Film film);
}

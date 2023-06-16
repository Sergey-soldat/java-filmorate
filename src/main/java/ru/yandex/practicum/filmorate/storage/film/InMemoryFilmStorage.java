package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int filmID = 1;

    private int generateID() {
        return filmID++;
    }

    @Override
    public Map<Integer, Film> getFilms() {
        return films;
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film createFilm(Film film) {
        film.setId(generateID());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        films.replace(film.getId(), film);
        return film;
    }

    @Override
    public Film getFilmById(int filmId) {
        return films.get(filmId);
    }

    @Override
    public void addLike(int filmId, int userId) {
        Film film = films.get(filmId);
        film.getUserIdLikes().add(userId);
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        Film film = films.get(filmId);
        film.getUserIdLikes().remove(userId);
    }
}

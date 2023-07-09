package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.dao.FilmDao;
import ru.yandex.practicum.filmorate.exception.FilmIdException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
@Slf4j
public class InMemoryFilmDao implements FilmDao {
    private final Map<Integer, Film> films = new HashMap<>();
    private int filmID = 1;

    private int generateID() {
        return filmID++;
    }

    @Override
    public Collection<Film> getFilms() {
        return films.values();
    }

//    @Override
//    public List<Film> getAllFilms() {
//        return new ArrayList<>(films.values());
//    }

    @Override
    public void createFilm(Film film) {
        film.setId(generateID());
        films.put(film.getId(), film);
    }

    @Override
    public void deleteFilm(Film film) {
        getFilms().remove(film.getId());
    }

    @Override
    public boolean updateFilm(Film film) {
        films.replace(film.getId(), film);
        return true;
    }

    @Override
    public Film getFilmById(Integer filmId) {
        return films.get(filmId);
    }

    public void validationId(Integer id) {
        if (!films.containsKey(id)) {
            throw new FilmIdException(String.format("Фильм с id %s не существует", id));
        }
    }

//    @Override
//    public void addLike(int filmId, int userId) {
//        Film film = films.get(filmId);
//        film.getUserIdLikes().add(userId);
//    }

//    @Override
//    public void deleteLike(int filmId, int userId) {
//        Film film = films.get(filmId);
//        film.getUserIdLikes().remove(userId);
//    }
}

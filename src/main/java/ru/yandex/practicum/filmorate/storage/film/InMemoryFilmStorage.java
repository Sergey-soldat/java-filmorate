package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.net.UnknownServiceException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private static final LocalDate FIRST_FILM_RELEASE = LocalDate.of(1895, 12, 28);
    private static final Map<Integer, Film> films = new HashMap<>();
    private static int filmID = 1;

    private int generateID() {
        return filmID++;
    }

    private void validate(Film film) {
        if (film.getReleaseDate().isBefore(FIRST_FILM_RELEASE)) {
            log.debug("Дата выпуска Film :{}", film.getReleaseDate());
            throw new ValidationException("Дата выпуска Film недействительна");
        }
    }

//    private int compareFilms(Film filmFirst, Film filmSecond) {
//        int result = 0;
//        if (filmFirst.getUserIdLikes().size() > filmSecond.getUserIdLikes().size()) {
//            result = -1;
//        }
//        return result;
//    }

    @Override
    public Map<Integer, Film> getFilms (){
        return films;
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film createFilm(Film film) {
        validate(film);
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

    @Override
    public List<Film> getPopularFilms(int count) {
        return films.values().stream()
//                .sorted(Comparator.comparingInt( (Film film) -> film.getUserIdLikes().size()))
                .sorted((f1, f2) -> f2.getUserIdLikes().size() - f1.getUserIdLikes().size())
//                .sorted(this::compareFilms)
                .limit(count)
                .collect(Collectors.toList());
    }
}

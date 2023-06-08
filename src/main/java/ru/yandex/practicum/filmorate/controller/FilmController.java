package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Film> getAll() {
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
        checkFilm(id, true);
        return filmService.getFilmById(id);
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        checkFilm(film.getId(), false);
        log.info("Фильм " + film.getName() + " с айди =" + film.getId() + " создан");
        return filmService.add(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        checkFilm(film.getId(), true);
        log.info("Фильм " + film.getName() + " с айди = " + film.getId() + " обновлен");
        return filmService.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        checkFilm(id, true);
        checkUser(userId);
        log.info("Пользователь  с айди =" + id + " поставил свой лайк Пользователю с айди" + userId);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        checkFilm(id, true);
        checkUser(userId);
        log.info("Пользователь  с айди = " + id + " удалил свой лайк у Пользователя с айди " + userId);
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getMostPopularFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.getMostPopularFilms(count);
    }

    private void checkUser(int userId) {
        if (userService.getUser(userId) == null) {
            throw new NotFoundException("Пользователь  с айди = " + userId + " не найден");
        }
    }

    private void checkFilm(int filmId, boolean isValid) {
        if (isValid) {
            if (filmService.getFilmById(filmId) == null) {
                throw new NotFoundException("Фильм с айди =" + filmId + " не найден");
            }
        } else if (filmId != 0 && filmService.getFilmById(filmId) != null) {
            throw new AlreadyExistException("Фильм с айди =" + filmId + " уже создан");
        }
    }
}
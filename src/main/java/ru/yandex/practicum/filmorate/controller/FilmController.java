package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;
//    private final UserService userService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
//        this.userService = userService;
    }

    @GetMapping
    public List<Film> getAll() {
        log.info("Все фильмы" + filmService.getAll());
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Integer id) {
//        checkFilm(id, true);
        log.info("Фильм с id" + id + " :" + filmService.getFilmById(id));
        return filmService.getFilmById(id);
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
//        checkFilm(film.getId(), false);
        log.info("Фильм " + film.getName() + " с айди =" + film.getId() + " создан");
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
//        checkFilm(film.getId(), true);
        log.info("Фильм " + film.getName() + " с айди = " + film.getId() + " обновлен");
        return filmService.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
//        checkFilm(id, true);
//        checkUser(userId);
        log.info("Пользователь  с id=" + userId + " поставил свой лайк фильму с id=" + id);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id, /*@PathVariable*/ int userId) {
//        checkFilm(id, true);
//        checkUser(userId);
        log.info("Пользователь  с айди = " + id + " удалил свой лайк у Пользователя с айди " + userId);
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getMostPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        log.info("Вывод популярных фильмов" + filmService.getMostPopularFilms(count));
        return filmService.getMostPopularFilms(count);
    }

//    private void checkUser(int userId) {
//        if (userService.getUser(userId) == null) {
//            log.debug("Пользователь  с id = " + userId + " не найден");
//            throw new NotFoundException("Пользователь  с айди = " + userId + " не найден");
//        }
//    }
//
//    private void checkFilm(int filmId, boolean isValid) {
//        if (isValid) {
//            if (filmService.getFilmById(filmId) == null) {
//                log.debug("Фильм  с id = " + filmId + " не найден");
//                throw new NotFoundException("Фильм с айди =" + filmId + " не найден");
//            }
//        } else if (filmId != 0 && filmService.getFilmById(filmId) != null) {
//            log.debug("Фильм с айди =" + filmId + " уже создан");
//            throw new AlreadyExistException("Фильм с айди =" + filmId + " уже создан");
//        }
//    }
}
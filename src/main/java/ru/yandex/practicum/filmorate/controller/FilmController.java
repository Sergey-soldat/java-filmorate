package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.*;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IDException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")

public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 1;
    private static final LocalDate FIRST_FILM_RELEASE = LocalDate.of(1895, 12, 28);

    private Integer generateID() {
        return id++;
    }

    private void containsKeyId(Film film) {
        if (films.containsKey(film.getId())) {
            log.debug("Film с id:{}", film.getId());
            throw new IDException("Id уже занят");
        }
    }

    @Valid
    @PostMapping
    public Film create(@RequestBody @Valid Film film) {
        containsKeyId(film);
        film.setId(generateID());
        validate(film);
        films.put(film.getId(), film);
        log.info("Создан Film с id:{}", film.getId());
        return film;
    }

    private void validate(Film film) {
        if (film.getReleaseDate().isBefore(FIRST_FILM_RELEASE)) {
            log.debug("Дата выпуска Film :{}", film.getReleaseDate());
            throw new ValidationException("Дата выпуска Film недействительна");
        }
        if (film.getName().isBlank() || film.getName() == null) {
            throw new ValidationException("Имя Film не может быть пустым");
        }
        if (film.getDuration() <= 0) {
            log.debug("Продолжительность Film: {}", film.getDuration());
            throw new ValidationException("Продолжительность Film не может быть отрицательным");
        }
        if (film.getDescription() == null || film.getDescription().isBlank() || film.getDescription().length() > 200) {
            throw new ValidationException("Описание Film не может быть больше 200 символов");
        }
    }

    @GetMapping
    public Collection<Film> getAll() {
        for (Integer id : films.keySet()) {
            log.info("Film с id" + id + "возвращён");
        }
        return films.values();
    }

    @Valid
    @PutMapping
    public Film put(@RequestBody @Valid Film film) {
        if ((id <= 0) || (film.getName() == null)) {
            throw new ValidationException("id должен быть больше 0");
        }
//        if ((film.getName() == null) || (film.getName().isEmpty())){
//            throw new ValidationException("Name должно быть заполненным");
//        }
        if (! films.containsKey(film.getId())) {
            log.debug("Film с id:{}", film.getId());
            throw new IDException("Id не обнаружен");
        }
//        containsKeyId(film);
        validate(film);
        films.put(film.getId(), film);
        log.info("Film с id:{} обновлен", film.getId());
        return film;
    }
}
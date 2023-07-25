package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.dao.db.GenreDb;


import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreDb genreDb;

    public Genre getGenreById(int id) {
        try {
            return genreDb.getGenreById(id);
        } catch (Exception e) {
            throw new NotFoundException("Жанр не найден");
        }
    }

    public List<Genre> getAllGenre() {
        try {
            return genreDb.getAllGenre();
        } catch (Exception e) {
            throw new NotFoundException("Жанры не найдены");
        }
    }

}

package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.db.GenreDb;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Service
public class GenreService {
    private final GenreDb genreStorage;

    public GenreService(GenreDb genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Collection<Genre> findAll() {
        return genreStorage.findAll();
    }

    public Genre getGenreById(int id) {
        return genreStorage.getById(id);
    }
}

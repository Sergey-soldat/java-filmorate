package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.dao.FilmDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component("inMemoryFilmDao")
//@Slf4j
public class InMemoryFilmDao implements FilmDao {
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 0;

    @Override
    public Collection<Film> getFilms() {
        return films.values();
    }

    @Override
    public Collection<Film> findAllTopFilms(Integer count) {
        List<Film> films = getFilms().stream().sorted(new FilmLikesComparator()).collect(Collectors.toList());
        return films.stream().limit(count).collect(Collectors.toList());
    }

    @Override
    public Film getFilmById(Integer id) {
        return films.get(id);
    }

    @Override
    public void createFilm(Film film) {
        film.setId(getId());
        films.put(film.getId(), film);
    }

    @Override
    public boolean updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return true;
        }
        return false;
    }

    @Override
    public void deleteFilm(Film film) {
        getFilms().remove(film.getId());
    }

    private int getId() {
        return ++id;
    }

    public void validationId(Integer id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException(String.format("Фильм с id %s не существует", id));
        }
    }

    static class FilmLikesComparator implements Comparator<Film> {

        @Override
        public int compare(Film o1, Film o2) {
            return o2.getRate() - o1.getRate();
        }
    }
}

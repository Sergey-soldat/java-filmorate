package ru.yandex.practicum.filmorate.dao.dao;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

public interface MpaDao {
    Collection<Mpa> findAll();

    Mpa getById(int id);

    void validationId(Integer id);
}

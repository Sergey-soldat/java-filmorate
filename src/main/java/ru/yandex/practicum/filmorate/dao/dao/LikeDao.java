package ru.yandex.practicum.filmorate.dao.dao;

import org.springframework.data.relational.core.sql.In;

public interface LikeDao {
    void addLike(Integer filmId, Integer userId);

    void deleteLike(Integer filmId, Integer userId);
}

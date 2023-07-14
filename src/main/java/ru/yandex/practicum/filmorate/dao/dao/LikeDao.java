package ru.yandex.practicum.filmorate.dao.dao;

public interface LikeDao {
    void addLike(Integer filmId, Integer userId);

    void deleteLike(Integer filmId, Integer userId);
}

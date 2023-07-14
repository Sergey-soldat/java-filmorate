package ru.yandex.practicum.filmorate.dao.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserDao {

    void createUser(User user);

    boolean updateUser(User user);

    User getUser(Integer userId);

    void deleteUser(Integer id);

    void validationId(Integer id);

    Collection<User> getUsers();
}

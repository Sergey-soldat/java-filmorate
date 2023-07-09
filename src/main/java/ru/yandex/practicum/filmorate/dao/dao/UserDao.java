package ru.yandex.practicum.filmorate.dao.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface UserDao {

    void createUser(User user);

    boolean updateUser(User user);

    User getUser(Integer userId);

    void deleteUser(int id);

    void validationId(Integer id);

    Collection<User> getUsers();
}

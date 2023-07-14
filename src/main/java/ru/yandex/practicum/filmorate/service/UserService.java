package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.db.UserDb;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.dao.dao.UserDao;

import java.util.Collection;

@Slf4j
@Service
public class UserService {
    private final UserDao userStorage;

    public UserService(UserDb userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> findAll() {
        log.debug("Текущее количество пользователей: {}", userStorage.getUsers().size());
        return userStorage.getUsers();
    }

    public User createUser(User user) {
        userStorage.createUser(user);
        return user;
    }

    public User update(User user) {
        if (userStorage.updateUser(user)) {
            return user;
        }
        log.debug("Пользователь с id: {} не найден", user.getId());
        return null;
    }

    public User getUser(Integer id) {
        return userStorage.getUser(id);
    }
}

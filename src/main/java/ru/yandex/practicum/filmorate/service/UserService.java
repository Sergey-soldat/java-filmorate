package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.dao.dao.UserDao;


import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public User addUser(User user) {
        if (validation(user)) {
            return userDao.addUser(user);
        } else {
            throw new ValidationException();
        }
    }

    public User updateUser(User user) {
        if (validation(user)) {
            if (userDao.checkUserExistInBd(user.getId())) {
                return userDao.updateUser(user);
            } else {
                throw new NotFoundException("Пользователь не найден");
            }
        } else {
            throw new ValidationException();
        }
    }


    public User getUserById(int id) {
        if (userDao.checkUserExistInBd(id)) {
            return userDao.getUserById(id);
        } else {
            throw new NotFoundException("Пользователь не найден");
        }
    }

    public List<User> getFriends(int id) {
        if (userDao.checkUserExistInBd(id)) {
            return userDao.getFriendsList(id);
        } else {
            throw new NotFoundException("Друг не найден");
        }
    }

    public User addFriend(Integer userId, Integer friendId) {
        if (checkUserAndFriend(userId, friendId)) {
            userDao.addFriend(userId, friendId);
            log.info("Добавлен друг в персону - {}", userId);
            return userDao.getUserById(userId);
        } else {
            throw new NotFoundException("Персона не найдена");
        }
    }

    public User deleteFriend(int userId, int friendId) {
        if (checkUserAndFriend(userId, friendId)) {
            userDao.deleteFriend(userId, friendId);
            log.info("Удален друг в персоне - {}", userId);
            return userDao.getUserById(userId);
        } else {
            throw new NotFoundException("Персона не найдена");
        }
    }

    public List<User> getCommonFriends(int firstUserId, int secondUserId) {
        if (checkUserAndFriend(firstUserId, secondUserId)) {
            Set<User> friendsFirstUser = new HashSet<>(userDao.getFriendsList(firstUserId));
            friendsFirstUser.retainAll(userDao.getFriendsList(secondUserId));
            List<User> mutualFriends = new ArrayList<>(friendsFirstUser);
            log.info("Выведен список общих друзей пользователей с id - {}, {}", firstUserId, secondUserId);
            return mutualFriends;
        } else {
            throw new NotFoundException("Персона не найдена");
        }
    }

    private boolean checkUserAndFriend(int id, int otherId) {
        return userDao.checkUserExistInBd(id) && userDao.checkUserExistInBd(otherId);
    }

    private boolean validation(User user) throws NullPointerException {
        if (user.getBirthday().isBefore(LocalDate.now()) && user.getEmail().contains("@")
                && !user.getLogin().isBlank()) {
            if (user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
            log.info("Пользователь обновлен/создан {}", user);
            return true;
        } else {
            throw new ValidationException();
        }
    }
}

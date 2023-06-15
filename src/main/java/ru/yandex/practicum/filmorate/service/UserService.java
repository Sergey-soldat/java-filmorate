package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.userStorage = inMemoryUserStorage;
    }

    private void validateEmail(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            log.debug("email не найден");
            throw new ValidationException("Can't update user with empty email");
        }
    }

    private void validateIfUserExist(int userId, boolean ifUserShouldExist) {
        if (ifUserShouldExist) {
            if ((!userStorage.getUsers().containsKey(userId)) || userId < 0) {
                log.debug("Не найден пользователь с id " + userId);
                throw new NotFoundException("User with id=" + userId + " is not exist");
            }
        } else if (userStorage.getUsers().containsKey(userId)) {
            log.debug("User with id=" + userId + " already exist");
            throw new AlreadyExistException("User with id=" + userId + " already exist");
        }
    }

    public static void loginInName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            log.info("Логин присвоен в роле имени");
            user.setName(user.getLogin());
        }
    }

    public List<User> getAll() {
        return userStorage.getAllUsers();
    }

    public User createUser(User user) {
        validateIfUserExist(user.getId(), false);
        loginInName(user);
        return userStorage.createUser(user);
    }

    public User update(User user) {
        if (user.getId() <= 0) {
            throw new ValidationException("Нельзя обновить Пользователя с айди =" + user.getId());
        }
        validateIfUserExist(user.getId(), true);
        validateEmail(user);
        loginInName(user);
        return userStorage.updateUser(user);
    }

    public User getUser(int userId) {
        validateIfUserExist(userId, true);
        return userStorage.getUser(userId);
    }

    public void addFriend(int userId, int friendId) {
        validateIfUserExist(userId, true);
        validateIfUserExist(friendId, true);
        userStorage.addFriend(userId, friendId);
        userStorage.addFriend(friendId, userId);
    }

    public void deleteFriend(int userId, int friendId) {
        validateIfUserExist(userId, true);
        validateIfUserExist(friendId, true);
        userStorage.deleteFriend(userId, friendId);
        userStorage.deleteFriend(friendId, userId);
    }

    public void deleteUser(User user) {
        validateIfUserExist(user.getId(), true);
        userStorage.deleteUser(user);
    }

    public List<User> getFriends(int userId) {
        return userStorage.getFriends(userId);
    }

    public List<User> getCommonFriends(int userId, int userIdToCompare) {
        validateIfUserExist(userId, true);
        validateIfUserExist(userIdToCompare, true);
        return userStorage.getCommonFriends(userId, userIdToCompare);
    }
}

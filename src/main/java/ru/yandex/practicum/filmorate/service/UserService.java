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

import javax.validation.Valid;
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

//    private void validateUserName(User user) {
//        if (user.getName() == null || user.getName().isEmpty()) {
//            log.debug("Имя пользователя пустое");
//            user.setName(user.getLogin());
//        }
//    }

    private void validateIfUserExist(int userId, boolean ifUserShouldExist) {
        if (ifUserShouldExist) {
//            if (getUser(userId) == null || userId < 0) {
//                log.debug("Не найден пользователь с id " + userId);
//                throw new NotFoundException("User with id=" + userId + " is not exist");
//            }
            if ((! userStorage.getUsers().containsKey(userId))) {
                log.debug("Не найден пользователь с id " + userId);
                throw new NotFoundException("User with id=" + userId + " is not exist");
            }
        } else
//            if (getUser(userId) != null) {
//            log.debug("User with id=" + userId + " already exist");
//            throw new AlreadyExistException("User with id=" + userId + " already exist");
//        }
        if (userStorage.getUsers().containsKey(userId) && userId != 0) {
            log.debug("User with id=" + userId + " already exist");
            throw new AlreadyExistException("User with id=" + userId + " already exist");
        }
    }

    public static void loginInName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.info("Логин присвоен в роле имени");
    }

    public List<User> getAll() {
        return userStorage.getAllUsers();
    }

    public User createUser(User user) {
//        validateIfUserExist(user.getId(), false);
//        loginInName(user);
        return userStorage.createUser(user);
    }

    public User update(User user) {
        if (user.getId() <= 0) {
            throw new ValidationException("Нельзя обновить Пользователя с айди =" + user.getId());
        }
        return userStorage.updateUser(user);
    }

    public User getUser(int userId) {
//        validateIfUserExist(userId, true);
        return userStorage.getUser(userId);
    }

    public void addFriend(int userId, int friendId) {
        userStorage.addFriend(userId, friendId);
        userStorage.addFriend(friendId, userId);
    }

    public void deleteFriend(int userId, int friendId) {
        userStorage.deleteFriend(userId, friendId);
        userStorage.deleteFriend(friendId, userId);
    }

    public void deleteUser(User user) {
        userStorage.deleteUser(user);
    }

    public List<User> getFriends(int userId) {
        return userStorage.getFriends(userId);
    }

    public List<User> getCommonFriends(int userId, int userIdToCompare) {
        return userStorage.getCommonFriends(userId, userIdToCompare);
    }
}

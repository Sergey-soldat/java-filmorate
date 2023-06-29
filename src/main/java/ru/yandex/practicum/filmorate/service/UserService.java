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
import java.util.Set;
import java.util.stream.Collectors;

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

    private static void loginInName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            log.info("Логин присвоен в роле имени");
            user.setName(user.getLogin());
        }
    }

    private void validate(User user) {
        if (user.getLogin().contains(" ")) {
            log.debug("Login User: {}", user.getLogin());
            throw new ValidationException("логин не может содержать пробелы");
        }
        UserService.loginInName(user);
    }

    public List<User> getAll() {
        return userStorage.getAllUsers();
    }

    public User createUser(User user) {
        validateIfUserExist(user.getId(), false);
        loginInName(user);
        validate(user);
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
        User user1 = userStorage.getUsers().get(userId);
        validate(user1);
        User user2 = userStorage.getUsers().get(userId);
        validate(user2);
        userStorage.addFriend(userId, friendId);
        userStorage.addFriend(friendId, userId);
    }

    public void deleteFriend(int userId, int friendId) {
        validateIfUserExist(userId, true);
        validateIfUserExist(friendId, true);
        userStorage.deleteFriend(userId, friendId);
        userStorage.deleteFriend(friendId, userId);
    }

    public void deleteUser(int id) {
        validateIfUserExist(id, true);
        userStorage.deleteUser(id);
    }

    public List<User> getFriends(int userId) {
        Set<Integer> friends = userStorage.getUsers().get(userId).getFriendsIds();
        return friends.stream()
                .map(userStorage.getUsers()::get)
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(int userId, int userIdToCompare) {
        validateIfUserExist(userId, true);
        validateIfUserExist(userIdToCompare, true);
        List<User> friendsUser1 = getFriends(userId);
        List<User> friendsUser2 = getFriends(userIdToCompare);
        return friendsUser1.stream()
                .filter(friendsUser2::contains)
                .collect(Collectors.toList());
    }
}

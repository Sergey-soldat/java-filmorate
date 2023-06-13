package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        validateIfUserExist(id, true);
        log.info("Все пользователи" + userService.getUser(id));
        return userService.getUser(id);
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        validateIfUserExist(user.getId(), false);
        validateUserName(user);
        log.info("User " + user.getEmail() + "was added");
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        validateIfUserExist(user.getId(), true);
        validateEmail(user);
        validateUserName(user);
        log.info("User " + user.getEmail() + "was updated");
        return userService.update(user);
    }

    @DeleteMapping
    public void deleteUser(@RequestBody User user) {
        validateIfUserExist(user.getId(), true);
        log.info("User with id=" + user.getId() + " has been deleted");
        userService.deleteUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        validateIfUserExist(id, true);
        validateIfUserExist(friendId, true);
        log.info("User with id=" + id + " add friend with id=" + friendId);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        validateIfUserExist(id, true);
        validateIfUserExist(friendId, true);
        log.info("User with id=" + id + " delete friend with id=" + friendId);
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        log.info("Все друзья пользователя с id" + id);
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        validateIfUserExist(id, true);
        validateIfUserExist(otherId, true);
        log.info("Общие друзья пользователей с id" + id + " и " + otherId);
        return userService.getCommonFriends(id, otherId);
    }

    private void validateEmail(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            log.debug("email не найден");
            throw new ValidationException("Can't update user with empty email");
        }
    }

    private void validateUserName(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            log.debug("Имя пользователя пустое");
            user.setName(user.getLogin());
        }
    }

    private void validateIfUserExist(int userId, boolean ifUserShouldExist) {
        if (ifUserShouldExist) {
            if (userService.getUser(userId) == null || userId < 0) {
                log.debug("Не найден пользователь с id " + userId);
                throw new NotFoundException("User with id=" + userId + " is not exist");
            }
        } else {
            if (userService.getUser(userId) != null) {
                log.debug("User with id=" + userId + " already exist");
                throw new AlreadyExistException("User with id=" + userId + " already exist");
            }
        }
    }
}

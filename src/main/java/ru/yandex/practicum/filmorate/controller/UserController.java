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

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        validateIfUserExist(id, true);
        return userService.getUser(id);
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        validateIfUserExist(user.getId(), false);
        validateUserName(user);
        log.info("User " + user.getEmail() + "was added");
        return userService.add(user);
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
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        validateIfUserExist(id, true);
        validateIfUserExist(otherId, true);
        return userService.getCommonFriends(id, otherId);
    }

    private void validateEmail(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ValidationException("Can't update user with empty email");
        }
    }

    private void validateUserName(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }

    private void validateIfUserExist(int userId, boolean ifUserShouldExist) {
        if (ifUserShouldExist) {
            if (userService.getUser(userId) == null || userId < 0) {
                throw new NotFoundException("User with id=" + userId + " is not exist");
            }
        } else {
            if (userService.getUser(userId) != null) {
                throw new AlreadyExistException("User with id=" + userId + " already exist");
            }
        }
    }
}

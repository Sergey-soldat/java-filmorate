package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/users")

public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    private int generateId() {
        return id++;
    }

    private void containsKeyId(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("Пользователь с id = {} обновлен", user.getId());
        }
    }

    private void loginInName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.info("Логин присвоен в роле имени");
    }

    @PostMapping
    public User create(@RequestBody @Valid User user) {
        containsKeyId(user);
        validate(user);
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("Создан User с id:{}", user.getId());
        return user;
    }

    private void validate(User user) {
        loginInName(user);
        if (user.getLogin().contains(" ")) {
            log.debug("Login User: {}", user.getLogin());
            throw new ValidationException("логин не может содержать пробелы");
        }
    }

    @GetMapping
    public Collection<User> getAll() {
        log.info("Возвращён список пользователей");
        return users.values();
    }

    @PutMapping
    public User put(@RequestBody @Valid User user) {
        if (!users.containsKey(user.getId())) {
            log.debug("Пользователь с таким id не найден");
            throw new ValidationException("User не найден");
        } else {
            validate(user);
            users.put(user.getId(), user);
            log.info("User с id:{} update", user.getId());
        }
        return user;
    }
}

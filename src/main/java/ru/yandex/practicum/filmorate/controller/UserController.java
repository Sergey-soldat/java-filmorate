package ru.yandex.practicum.filmorate.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Data
@RestController
@Slf4j
@Validated
@RequestMapping("/users")

public class UserController {
    private Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    private int generateId(){
        return id++;
    }

    @PostMapping
    public User create(@RequestBody @Valid User user){
        if (users.containsValue(user)){
            users.put(user.getId(), user);
            log.info("Пользователь с id = {} обновлен", user.getId());
        }
        validate(user);
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("Создан User с id:{}", user.getId());
        return user;
    }

    private void validate(User user){
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getLogin() == null || user.getLogin().isBlank()) {
            throw new ValidationException("логин не может быть пустым");
        }
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("логин не может содержать пробелы");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            user.setEmail(user.getEmail());
            log.debug("У User с id:{} нет почты", user.getId());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("дата рождения должна быть не будущей");
        }
    }

    @GetMapping
    public Collection<User> getAll (){
        return users.values();
    }

    @PutMapping
    public User put(@RequestBody @Valid User user) {
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("User не найден");
        } else {
            validate(user);
            users.put(user.getId(), user);
            log.info("User с id:{} update", user.getId());
        }
        return user;
    }
}

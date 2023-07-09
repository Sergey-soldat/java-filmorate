package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.dao.UserDao;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.UserIdException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
@Slf4j
public class InMemoryUserDao implements UserDao {
    private final Map<Integer, User> users = new HashMap<>();
    private int userID = 0;

    public List<User> getFriendsList(Set<Integer> friendsId) {
        return getFriends(friendsId);
    }

    public List<User> getFriends(Set<Integer> friendsId) {
        List<User> friends = new ArrayList<>();
        for (Integer id : friendsId) {
            friends.add(getUser(id));
        }
        return friends;
    }

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }

    @Override
    public void createUser(User user) {
        for (User u : getUsers()) {
            if (u.getEmail().equals(user.getEmail())) {
                throw new UserAlreadyExistException(String.format(
                        "Пользователь с электронной почтой %s уже зарегистрирован.",
                        user.getEmail()
                ));
            }
        }
        user.setId(getId());
        users.put(user.getId(), user);
    }

    @Override
    public boolean updateUser(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return true;
        }
        return false;
    }

    @Override
    public void deleteUser(int id) {
        users.remove(id);
    }

    @Override
    public User getUser(Integer id) {
        validationId(id);
        return users.get(id);
    }

    private int getId() {
        return ++userID;
    }

    public void validationId(Integer id) {
        if (!users.containsKey(id)) {
            throw new UserIdException(String.format("Пользователь с id %s не существует", id));
        }
    }
}
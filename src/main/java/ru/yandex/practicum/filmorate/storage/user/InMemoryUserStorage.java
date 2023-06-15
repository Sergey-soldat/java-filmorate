package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private static final Map<Integer, User> users = new HashMap<>();
    private static int userID = 1;

    private int generateID() {
        return userID++;
    }

    private void validate(User user) {
        if (user.getLogin().contains(" ")) {
            log.debug("Login User: {}", user.getLogin());
            throw new ValidationException("логин не может содержать пробелы");
        }
        UserService.loginInName(user);
    }

    @Override
    public Map<Integer, User> getUsers() {
        return users;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User createUser(User user) {
        user.setId(generateID());
        validate(user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (users.containsKey(user.getId())) {
            users.replace(user.getId(), user);
        }
        return user;
    }

    @Override
    public User getUser(int userId) {
        return users.get(userId);
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user.getId());
    }

    @Override
    public void addFriend(int userId, int friendId) {
        User user = users.get(userId);
        validate(user);
        user.getFriendsIds().add(friendId);
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
        User user = users.get(userId);
        user.getFriendsIds().remove(friendId);
    }

    @Override
    public List<User> getFriends(int userId) {
        Set<Integer> friends = users.get(userId).getFriendsIds();
        return friends.stream()
                .map(users::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getCommonFriends(int userId, int userIdToCompare) {
        List<User> friendsUser1 = getFriends(userId);
        List<User> friendsUser2 = getFriends(userIdToCompare);
        return friendsUser1.stream()
                .filter(friendsUser2::contains)
                .collect(Collectors.toList());
    }
}

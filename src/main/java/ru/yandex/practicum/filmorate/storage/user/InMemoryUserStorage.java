package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int userID = 1;

    private int generateID() {
        return userID++;
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
    public void deleteUser(int id) {
        users.remove(id);
    }

    @Override
    public void addFriend(int userId, int friendId) {
        User user1 = users.get(userId);
        user1.getFriendsIds().add(friendId);
        User user2 = users.get(friendId);
        user2.getFriendsIds().add(userId);
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
        User user1 = users.get(userId);
        user1.getFriendsIds().remove(friendId);
        User user2 = users.get(friendId);
        user2.getFriendsIds().remove(userId);
    }
}

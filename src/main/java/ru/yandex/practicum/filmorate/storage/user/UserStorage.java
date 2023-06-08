package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    List<User> getAllUsers();

    User createUser(User user);

    User updateUser(User user);

    User getUser(int userId);

    void addFriend(int userId, int friendId);

    void deleteFriend(int userId, int friendId);

    void deleteUser(User user);

    List<User> getFriends(int userId);

    List<User> getCommonFriends(int userId, int userIdToCompare);
}

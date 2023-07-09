package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dao.dao.FriendsDao;
import ru.yandex.practicum.filmorate.dao.dao.UserDao;
import ru.yandex.practicum.filmorate.dao.db.FriendsDb;
import ru.yandex.practicum.filmorate.dao.db.UserDb;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class FriendsService {
    private final FriendsDao friendStorage;
    private final UserDao userStorage;

    public FriendsService(FriendsDb friendStorage, UserDb userStorage) {
        this.friendStorage = friendStorage;
        this.userStorage = userStorage;
    }

    public void deleteFriend(Integer id, Integer friendId) {
        friendStorage.deleteFriend(id, friendId);
    }

    public List<User> getFriendsList(Integer userId) {
        return friendStorage.getFriendsList(userId);
    }

    public List<User> getCommonFriends(Integer id, Integer otherId) {
        List<Integer> friendsId = friendStorage.getCommonFriends(id, otherId);
        return friendsId.stream().map(userStorage::getUser).collect(Collectors.toList());
    }

    public void addFriend(Integer id, Integer otherId) {
        friendStorage.addFriend(id, otherId);
    }
}

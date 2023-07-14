package ru.yandex.practicum.filmorate.dao.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendsDao {

    void deleteFriend(Integer id, Integer friendId);

    List<User> getFriendsList(Integer userId);

    List<Integer> getCommonFriends(Integer id, Integer otherId);

    void addFriend(Integer id, Integer friendId);
}

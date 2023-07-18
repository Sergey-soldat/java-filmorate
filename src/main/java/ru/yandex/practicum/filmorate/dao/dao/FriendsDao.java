package ru.yandex.practicum.filmorate.dao.dao;

import java.util.Collection;
import java.util.List;

public interface FriendsDao {

    void deleteFriend(Integer id, Integer friendId);

    List<Integer> getCommonFriends(Integer id, Integer otherId);

    void addFriend(Integer id, Integer friendId);

    Collection<Integer> getUserFriendsById(int userId);
}

package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Friends {
    private int userId;
    private int friendId;
    private boolean friendsStatus = false;

    public Friends(int user_id, int friend_id) {
        this.userId = user_id;
        this.friendId = friend_id;
    }
}

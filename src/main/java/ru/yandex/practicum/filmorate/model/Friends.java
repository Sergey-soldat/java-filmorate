package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Friends {
    private int userId;
    private int friendId;
    private boolean friendsStatus = false;

    public Friends(int userId, int friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }
}

package ru.yandex.practicum.filmorate.dao.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.dao.FriendsDao;
import ru.yandex.practicum.filmorate.dao.dao.UserDao;

import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class FriendsDb implements FriendsDao {
    private final JdbcTemplate jdbcTemplate;
    private final UserDao userDao;

    public FriendsDb(JdbcTemplate jdbcTemplate, UserDb userDb) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDb;
    }

    @Override
    public void deleteFriend(Integer id, Integer friendId) {
        String sql = "DELETE FROM FRIENDS WHERE USER_ID = ? AND FRIEND_ID = ?";
        jdbcTemplate.update(sql, id, friendId);
        log.info("Пользователь с id {} удаляет пользователя с id {}", id, friendId);
    }

    @Override
    public Collection<Integer> getUserFriendsById(int userId) {
        log.debug("Получение списка друзей пользователя с id {}", userId);
        String sql = "select FRIEND_ID from FRIENDS where USER_ID = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, userId);
    }

    @Override
    public List<Integer> getCommonFriends(Integer id, Integer otherId) {
        log.info("Список общих друзей пользователей с id {} и {}", id, otherId);
        userDao.validationId(id);
        userDao.validationId(otherId);
        String sql = "SELECT FRIEND_ID " +
                "FROM friends " +
                "WHERE  user_id = ? " +
                "INTERSECT " +
                "SELECT FRIEND_ID " +
                "FROM friends " +
                "WHERE  user_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("friend_id"), id, otherId);
    }

    @Override
    public void addFriend(Integer id, Integer friendId) {
        log.info("Пользователь с id {} добавляет пользователя с id {}", id, friendId);
        userDao.validationId(id);
        userDao.validationId(friendId);
        String sql = "MERGE INTO FRIENDS KEY (USER_ID, FRIEND_ID, FRIENDS_STATUS) VALUES ( ?, ?, ? )";
        jdbcTemplate.update(sql, id, friendId, false);
        if (checkFriends(id, friendId)) {
            jdbcTemplate.update("UPDATE FRIENDS SET FRIENDS_STATUS=true " + "WHERE USER_ID IN (?,?) AND FRIEND_ID IN (?, ?)", id, friendId, friendId, id);
            log.info("Статус дружбы пользователей {} и {} = true", id, friendId);
        }
    }

    private boolean checkFriends(int userId, int friendId) {
        String sql = "SELECT COUNT(*) FROM FRIENDS WHERE USER_ID IN (?, ?) AND FRIEND_ID IN (?, ?);";
        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(sql, userId, friendId, friendId, userId);
        if (resultSet.next()) {
            int result = resultSet.getInt("COUNT(*)");
            return result == 2;
        }
        return false;
    }
}

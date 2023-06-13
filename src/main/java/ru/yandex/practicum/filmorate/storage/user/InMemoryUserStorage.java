package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
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

//    private void loginInName(User user) {
//        if (user.getName() == null || user.getName().isBlank()) {
//            user.setName(user.getLogin());
//        }
//        log.info("Логин присвоен в роле имени");
//    }

    private void validate(User user) {
        if (user.getLogin().contains(" ")) {
            log.debug("Login User: {}", user.getLogin());
            throw new ValidationException("логин не может содержать пробелы");
        }
        UserService.loginInName(user);
    }

    @Override
    public Map<Integer, User> getUsers(){
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
//        if (user.getId() <= 0) {
//            throw new ValidationException("Нельзя обновить Пользователя с айди =" + user.getId());
//        }
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

//    @Override
//    public List<User> getFriends(int userId) {
//        User user = users.get(userId);
//        Set<Integer> listFriendsIds = user.getFriendsIds();
//        List<User> friendList = new ArrayList<>();
//        for (Integer friendId : listFriendsIds) {
//            friendList.add(users.get(friendId));
//        }
//        return friendList;
//    }

    @Override
    public List<User> getFriends(int userId) {
        Set<Integer> friends = users.get(userId).getFriendsIds();
        return friends.stream()
                .map(users::get)
                .collect(Collectors.toList());
    }

//    @Override
//    public List<User> getCommonFriends(int userId, int userIdToCompare) {
//        User userFirst = users.get(userId);
//        User userSecond = users.get(userIdToCompare);
//        Set<Integer> commonFriendsList = new HashSet<>();
//        for (Integer friendsUserFirst : userFirst.getFriendsIds()) {
//            for (Integer friendsUserSecond : userSecond.getFriendsIds()) {
//                if (friendsUserFirst.equals(friendsUserSecond)) {
//                    commonFriendsList.add(friendsUserFirst);
//                }
//            }
//        }
//        List<User> commonFriends = new ArrayList<>();
//        for (Integer friendId : commonFriendsList) {
//            commonFriends.add(users.get(friendId));
//        }
//        return commonFriends;
//    }

//    public Collection<User> getCommonFriends(int firstUserId, int secondUserId) {
//        //первый пользователь
//        User firstUser = getUser(firstUserId);
//        //второй пользователь
//        User secondUser = getUser(secondUserId);
//
//        Set<Long> commonFriends = new HashSet<>(firstUser.getFriends());
//        commonFriends.retainAll(secondUser.getFriendsIds());
//        return commonFriends.stream()
//                .map(userStorage::getUserById)
//                .collect(Collectors.toList());
//    }
//    public List<Integer> getCommonFriends2(int userId1, int userId2) {
//        Set<Integer> friendsUser1 = users.get(userId1).getFriendsIds();
//        Set<Integer> friendsUser2 = users.get(userId2).getFriendsIds();
//        return friendsUser1.stream()
//            .filter(friendsUser2::contains)
//                .collect(Collectors.toList());
//    }
    @Override
    public List<User> getCommonFriends(int userId, int userIdToCompare) {
        List<User> friendsUser1 = getFriends(userId);
        List<User> friendsUser2 = getFriends(userIdToCompare);
        return friendsUser1.stream()
                .filter(friendsUser2::contains)
                .collect(Collectors.toList());
    }
}

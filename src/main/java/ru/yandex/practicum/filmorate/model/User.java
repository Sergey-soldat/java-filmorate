package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    @NotNull
    @Email(message = "email должно содержать символы или цифры")
    private String email;
    @NotNull
    @NotBlank(message = "Логин не может быть пустым!")
    private String login;
    @Nullable
    private String name;
    @PastOrPresent
    private LocalDate birthday;
    private boolean statusFriend; // 0(false) неподтверждённая, 1(true) - иначе.
    private Set<Integer> friendsIds = new HashSet<>();
}

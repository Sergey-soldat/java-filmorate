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
    private int id= 0;
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
    public User(@NonNull String email, @NonNull String login, String name, @NonNull LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = setName(name);
        this.birthday = birthday;
    }

    private String setName(String name) {
        if (name == null || name.isBlank()) {
            return login;
        }
        return name;
    }
//    private Set<Integer> friendsIds = new HashSet<>();
}

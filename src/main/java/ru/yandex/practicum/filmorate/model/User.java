package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@NotNull
@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {

    @NotNull
    private Integer id;
    @Email(message = "email должно содержать символы или цифры")
    String email;
    private String name;
    @NotBlank(message = "Логин не может быть пустым!")
    private String login;

    @Past
    @NonNull
    private LocalDate birthday;
}

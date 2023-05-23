package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.*;
import java.time.LocalDate;

@NotNull
@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {

    @NotNull
    private Integer id;
    @NotNull
    @NotBlank
    @Email(message = "email должно содержать символы или цифры")
    private String email;
    @NotNull
    @NotBlank(message = "Имя должно содержать символы")
    private String name;
    @NotNull
    @NotBlank(message = "Логин не может быть пустым!")
    private String login;
    @NonNull
    @PastOrPresent
    private LocalDate birthday;
}

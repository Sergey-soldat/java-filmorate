package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {

    private Integer id;
    @Email(message = "email должно содержать символы или цифры")
    @NotNull
    private String email;
    private String name;
    @NotBlank(message = "Логин не может быть пустым!")
    private String login;
    @PastOrPresent
    private LocalDate birthday;
}

package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@NotNull
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Film {

    @NotNull
    private Integer id;
    @NotNull
    @NotBlank(message = "Имя должно содержать символы")
    private String name;
    @NotNull
    @NotBlank(message = "Описание должно содержать символы")
    @Size(max = 200, message = "вместимость описания до 200 символов")
    private String description;
    @PastOrPresent
    private LocalDate releaseDate;
    @NotNull
    @NotBlank
    @Positive(message = "продолжительность должна быть не отрицательной")
    private Integer duration;
}

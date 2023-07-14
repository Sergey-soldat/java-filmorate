package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.validation.CheckReleaseDate;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private int id = 0;
    @NotBlank(message = "Название фильма не может быть пустым")
    private final String name;
    @Size(max = 200, message = "Описание фильма превышает 200 символов")
    private final String description;
    @CheckReleaseDate
    private final LocalDate releaseDate;
    @PositiveOrZero(message = "Продолжительность фильма не может быть отрицательной")
    private final int duration;
    private Mpa mpa;
    private int rate;
    private final Set<Genre> genres = new HashSet<>();
}

package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private int id = 0;
    @NotNull(message = "Имя должно содержать символы")
    @NotEmpty
    private String name;
    @Size(max = 200, message = "вместимость описания до 200 символов")
    private String description;
    @PastOrPresent(message = "дата выпуска не должна быть будущей")
    private LocalDate releaseDate;
    @Positive(message = "продолжительность должна быть отрицательной")
    private int duration;
    private Set<Integer> userIdLikes = new HashSet<>();
    private Mpa mpa;
//    private int rate;
    private final Set<Genre> genres = new HashSet<>();

    public Film(String name, String description, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}

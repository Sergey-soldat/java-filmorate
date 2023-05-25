package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {

    private Integer id;
    @NotBlank(message = "Имя должно содержать символы")
    private String name;
    @Size(max = 200, message = "вместимость описания до 200 символов")
    private String description;
    @PastOrPresent
    private LocalDate releaseDate;
    @Positive(message = "продолжительность должна быть не отрицательной")
    private Integer duration;
}

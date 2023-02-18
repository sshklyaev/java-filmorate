package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Data
@Builder
public class Film {
    @NotNull
    private Integer id;
    @NotBlank
    private String name;
    @Size(min = 1, max = 200)
    @NotNull
    private String description;
    @NotNull
    @DateTimeFormat(pattern="yyyy/MM/dd")
    private LocalDate releaseDate;
    @NotNull
    private Integer duration;

}

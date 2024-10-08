package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private Long id;
    private Set<Long> likes = new HashSet<>();
    @NotNull
    private LocalDate releaseDate;
    @NotBlank  @NotNull
    private String name;
    @Size(min = 1, max = 200)
    @NotNull
    private String description;
    @Positive
    private Integer duration;
    private Set<Genre> genres;

    private Mpa mpa;

}

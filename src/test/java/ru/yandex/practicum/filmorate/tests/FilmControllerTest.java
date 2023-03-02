
package ru.yandex.practicum.filmorate.tests;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;
import service.FilmService;
import storage.FilmStorage;
import storage.InMemoryFilmStorage;
import storage.InMemoryUserStorage;
import storage.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest

public class FilmControllerTest {
    private Film film;
   private FilmController filmController;
    private FilmStorage filmStorage;
    private UserStorage userStorage;
    @BeforeEach
    public void beforeEach() {
        filmStorage = new InMemoryFilmStorage();
        InMemoryUserStorage userStorage = new InMemoryUserStorage();
        filmController = new FilmController(filmStorage, new FilmService(filmStorage, userStorage));
        film = Film.builder()
                .name("Breakfast at Tiffany's")
                .description("American romantic comedy film directed by Blake Edwards, written by George Axelrod," +
                        " adapted from Truman Capote's 1958 novella of the same name.")
                .releaseDate(LocalDate.of(1961,10,5))
                .duration(114)
                .build();
    }

    @Test
    public void shouldNoAddFilmWhenFilmReleaseDateIsBefore28121895() {
        film.setReleaseDate(LocalDate.of(1895,12,27));
        assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals(0, filmController.getFilms().size(), "Список фильмов должен быть пустым");
    }
}

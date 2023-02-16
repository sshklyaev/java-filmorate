package tests;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controller.FilmController;
import model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilmControllerTest {
    private Film film;
    private FilmController filmController;

    @BeforeEach
    public void beforeEach() {
        filmController = new FilmController();
        film = new Film();
        film.setName("Java Filmorate test");
        film.setDescription("I love java!");
        film.setDuration(100);
        film.setReleaseDate(LocalDate.of(2000, 1, 6));
    }




    @Test
    public void shouldNoAddFilmWhenFilmIsNull() {
        film = null;
        Object object = filmController.saveFilm(film);
        assertTrue(object.toString().contains("BAD_REQUEST"), "Должен быть получен 400 ответ от сервера");
        assertEquals(0, filmController.getFilms().size(), "Список фильмов должен быть пустым");
    }


    @Test
    public void shouldNoAddFilmWhenFilmNameIsNull() {
        film.setName(null);
        Object object = filmController.saveFilm(film);
        assertTrue(object.toString().contains("BAD_REQUEST"), "Должен быть получен 400 ответ от сервера");
        assertEquals(0, filmController.getFilms().size(), "Список фильмов должен быть пустым");
    }


    @Test
    public void shouldNoAddFilmWhenFilmNameIsEmpty() {
        film.setName("");
        Object object = filmController.saveFilm(film);
        assertTrue(object.toString().contains("BAD_REQUEST"), "Должен быть получен 400 ответ от сервера");
        assertEquals(0, filmController.getFilms().size(), "Список фильмов должен быть пустым");
    }


    @Test
    public void shouldNoAddFilmWhenFilmDescriptionMoreThan200Symbols() {
        film.setDescription(film.getDescription() + film.getDescription());
        Object object = filmController.saveFilm(film);
        assertTrue(object.toString().contains("BAD_REQUEST"), "Должен быть получен 400 ответ от сервера");
        assertEquals(0, filmController.getFilms().size(), "Список фильмов должен быть пустым");
    }


    @Test
    public void shouldNoAddFilmWhenFilmDescriptionIsNull() {
        film.setDescription(null);
        Object object = filmController.saveFilm(film);
        assertTrue(object.toString().contains("BAD_REQUEST"), "Должен быть получен 400 ответ от сервера");
        assertEquals(0, filmController.getFilms().size(), "Список фильмов должен быть пустым");
    }


    @Test
    public void shouldNoAddFilmWhenFilmReleaseDateIsBefore28121895() {
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        Object object = filmController.saveFilm(film);
        assertTrue(object.toString().contains("BAD_REQUEST"), "Должен быть получен 400 ответ от сервера");
        assertEquals(0, filmController.getFilms().size(), "Список фильмов должен быть пустым");
    }



    @Test
    public void shouldNoAddFilmWhenFilmDurationIsZero() {
        film.setDuration(0);
        Object object = filmController.saveFilm(film);
        assertTrue(object.toString().contains("BAD_REQUEST"), "Должен быть получен 400 ответ от сервера");
        assertEquals(0, filmController.getFilms().size(), "Список фильмов должен быть пустым");
    }


    @Test
    public void shouldNoAddFilmWhenFilmDurationIsNegative() {
        film.setDuration(-1);
        Object object = filmController.saveFilm(film);
        assertTrue(object.toString().contains("BAD_REQUEST"), "Должен быть получен 400 ответ от сервера");
        assertEquals(0, filmController.getFilms().size(), "Список фильмов должен быть пустым");
    }


    @Test
    public void shouldNoAddFilmWhenFilmDurationIsNull() {
        film.setDuration(null);
        Object object = filmController.saveFilm(film);
        assertTrue(object.toString().contains("BAD_REQUEST"), "Должен быть получен 400 ответ от сервера");
        assertEquals(0, filmController.getFilms().size(), "Список фильмов должен быть пустым");
    }
}


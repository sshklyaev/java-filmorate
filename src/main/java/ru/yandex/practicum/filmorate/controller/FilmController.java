package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.Exception.ValidationException;

import java.time.LocalDate;
import java.util.*;


import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private Map<Integer, Film> films = new HashMap<>();
    private Integer currentId = 1;
    private static final LocalDate AFTER_RELEASE_DATE = LocalDate.of(1895, 12, 28);


    @GetMapping
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }


    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Получен POST-запрос к эндпоинту: '/films' на добавление фильма с ID={}", currentId + 1);
        if (isValidFilm(film)) {
            film.setId(++currentId);
            films.put(film.getId(), film);
        }

        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Получен PUT-запрос к эндпоинту: '/films' на обновление фильма с ID={}", film.getId());
        if (film.getId() == null) {
            throw new ValidationException("Передан пустой аргумент!");
        }
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Фильм с ID=" + film.getId() + " не найден!");
        }
        if (isValidFilm(film)) {
            films.put(film.getId(), film);
        }
        return film;

    }

    private boolean isValidFilm(Film film) {
        if (film.getReleaseDate().isBefore(AFTER_RELEASE_DATE)) {
            throw new ValidationException("Некорректная дата релиза фильма: " + film.getReleaseDate());
        }

        return true;
    }


}
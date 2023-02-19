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
public class FilmController {
    private Map<Integer, Film> films;
    private Integer currentId;

    public FilmController() {
        currentId = 0;
        films = new HashMap<>();
    }

    @GetMapping("/films")
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }


    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) {
        log.info("Получен POST-запрос к эндпоинту: '/films' на добавление фильма с ID={}", currentId + 1);

            film.setId(++currentId);
            films.put(film.getId(), film);

        return film;
    }

    @PutMapping(value = "/films")
    public Film update(@Valid @RequestBody Film film) {
        log.info("Получен PUT-запрос к эндпоинту: '/films' на обновление фильма с ID={}", film.getId());
        if (isContainsFilm(film.getId()) == true) {

                films.put(film.getId(), film);
                currentId++;

        }
        return film;
    }


    private boolean isContainsFilm(int id) {
        return films.containsKey(id);
    }
}
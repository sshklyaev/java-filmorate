package controller;

import lombok.extern.slf4j.Slf4j;
import model.Film;
import Exception.ValidationException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class FilmController {
    private Map<Integer, Film> films = new HashMap<>();
    private Integer currentId;

    public FilmController() {
        currentId = 0;
        films = new HashMap<>();
    }

    @GetMapping("/films")
    public Collection<Film> getFilms() {
        return films.values();
    }

    @ResponseBody
    @PostMapping(value = "/films")
    public Object saveFilm(@RequestBody Film film) {
        try {
            log.info("Получен POST-запрос к эндпоинту: '/films' на добавление фильма с ID={}", currentId + 1);
            if (isValidFilm(film)) {
                film.setId(++currentId);
                films.put(film.getId(), film);
            }
        } catch (ValidationException exp) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        } catch (NullPointerException exp) {
            log.error("Передан пустой аргумент!");
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        return film;

    }

    @ResponseBody
    @PutMapping(value = "/films")
    public Object updateFilm(@RequestBody Film film) {
        try {
            log.info("Получен PUT-запрос к эндпоинту: '/films' на обновление фильма с ID={}", film.getId());
            if (film.getId() == null) {
                film.setId(currentId + 1);
            }
            if (isValidFilm(film)) {
                films.put(film.getId(), film);
                currentId++;
            }
        } catch (ValidationException exp) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        } catch (NullPointerException exp) {
            log.error("Передан пустой аргумент!");
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        return film;
    }

    private boolean isValidFilm(Film film) {
        if (film.getName().isEmpty()) {
            throw new ValidationException("Название фильма не должно быть пустым!");
        }
        if (film.getDescription().length() <= 200) {
            throw new ValidationException("Описание фильма больше 200 символов: " + film.getDescription().length());
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Некорректная дата релиза фильма: " + film.getReleaseDate());
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность должна быть положительной: " + film.getDuration());
        }
        return true;
    }
}

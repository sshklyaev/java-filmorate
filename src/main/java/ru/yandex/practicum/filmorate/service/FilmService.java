package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.Exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private FilmStorage filmStorage;
    private UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId);
        if (film != null) {
            if (userStorage.getUserById(userId) != null) {
                film.getLikes().add(userId);
            } else {
                throw new UserNotFoundException("Пользователь c ID=" + userId + " не найден!");
            }
        } else {
            throw new FilmNotFoundException("Фильм c ID=" + filmId + " не найден!");
        }
    }

    public void deleteLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId);
        if (film != null) {
            if (film.getLikes().contains(userId)) {
                film.getLikes().remove(userId);
            } else {
                throw new UserNotFoundException("Лайк от пользователя c ID=" + userId + " не найден!");
            }
        } else {
            throw new FilmNotFoundException("Фильм c ID=" + filmId + " не найден!");
        }
    }

    public List<Film> getPopular(Integer count) {
        if (count < 1) {
            new ValidationException("Количество фильмов для вывода не должно быть меньше 1");
        }
        return filmStorage.getFilms().stream()
                .sorted((o1, o2) -> o2.getLikes().size() - o1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }


    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }


    public Film create(Film film) {
        return filmStorage.create(film);
    }


    public Film update(Film film) {
        return filmStorage.update(film);
    }


    public Film getFilmById(Long filmId) {
        return filmStorage.getFilmById(filmId);
    }


    public Film delete(Long filmId) {
         return filmStorage.delete(filmId);
    }
}
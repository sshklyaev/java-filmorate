package ru.yandex.practicum.filmorate.Exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenreNotFoundException extends IllegalArgumentException {
    public GenreNotFoundException(String message) {

        log.error(message);
    }
}

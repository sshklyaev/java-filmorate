package ru.yandex.practicum.filmorate.Exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MpaNotFoundException extends IllegalArgumentException {
    public MpaNotFoundException(String message) {

        log.error(message);
    }
}


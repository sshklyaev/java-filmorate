package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreDao {

    public Genre getGenreById(Integer id);

    public List<Genre> getAllGenres();
}

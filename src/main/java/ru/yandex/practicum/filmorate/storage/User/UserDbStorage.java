package ru.yandex.practicum.filmorate.storage.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component("userDbStorage")
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getUsers() {
        String sql = "select * from users";
        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
        for (User user : users) {
            user.setFriends(getUserFriendsFromDb(user));
        }
        return users;
    }

    @Override
    public User create(User user) {
        String sql = "insert into users (login, name, email, birthday) values (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"user_id"});
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, keyHolder);
        user.setId(keyHolder.getKey().longValue());
        return getUserById(user.getId());
    }

    @Override
    public User update(User user) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from users where user_id = ?", user.getId());
        if (userRows.next()) {
            jdbcTemplate.update("update users set login = ?, name = ?, email = ?, birthday = ? " +
                            "where user_id = ?",
                    user.getLogin(),
                    user.getName(),
                    user.getEmail(),
                    user.getBirthday(),
                    user.getId());
            updateFriends(user);
            return getUserById(user.getId());
        } else {
            throw new UserNotFoundException("Пользователь не был найден!");
        }
    }

    @Override
    public User getUserById(Long userId) {
        String sql = "select * from users where user_id = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeUser(rs), userId);
            user.setFriends(getUserFriendsFromDb(user));
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("Пользователь не был найден!");
        }
    }

    public void updateFriends(User user) {
        jdbcTemplate.update("delete from friends where user_id = ?", user.getId());
        if (user.getFriends() != null) {
            for (Long friendId : user.getFriends()) {
                jdbcTemplate.update("insert into friends (user_id, friend_id) values (?, ?)",
                        user.getId(), friendId);
            }
        }
    }

    public Set<Long> getUserFriendsFromDb(User user) {
        String sql = "select friend_id from friends where user_id = ?";
        List<Long> friends = jdbcTemplate.queryForList(sql, Long.class, user.getId());
        return new HashSet<>(friends);
    }

    public User makeUser(ResultSet rs) throws SQLException {
        Long id = rs.getLong("user_id");
        String login = rs.getString("login");
        String name = rs.getString("name");
        String email = rs.getString("email");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();

        return User.builder()
                .id(id)
                .name(name)
                .login(login)
                .email(email)
                .birthday(birthday)
                .build();
    }

}
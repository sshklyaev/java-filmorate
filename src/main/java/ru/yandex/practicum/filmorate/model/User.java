package ru.yandex.practicum.filmorate.model;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class User {
    private Long id;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Pattern(regexp = "\\S*$")
    private String login;
    private String name;
    @NotNull
    @PastOrPresent
    private LocalDate birthday;
    private Set<Long> friends = new HashSet<>();

    public User(Long id, String email, String login, String name, LocalDate birthday, Set<Long> friends) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        this.friends = friends;
    }
}

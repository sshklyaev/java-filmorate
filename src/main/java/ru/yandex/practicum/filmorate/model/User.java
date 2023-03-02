package ru.yandex.practicum.filmorate.model;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

import java.time.LocalDate;
@Data
@Builder
public class User {
    private Integer id;
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

    public User(Integer id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}

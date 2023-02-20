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
    @NotEmpty
    @Pattern(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            message = "Неккоректный адресс электронной почты!")
    private String email;
    @NotBlank
    @Pattern(regexp = "\\S*$")
    private String login;
    private String name;
    @NotNull
    @PastOrPresent
    private LocalDate birthday;
}

package ru.lopatuxin.scola.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class StudentDTO {

    @NotEmpty(message = "Введите имя")
    @Size(min = 3, max = 50, message = "Имя должно быть не меньше 3 символов и не больше 50")
    private String name;

    @NotEmpty(message = "Введите фамилию")
    @Size(min = 3, max = 100, message = "Фамилия должна быть не меньше 3 символов и не больше 100")
    private String surname;

    @EqualsAndHashCode.Include
    @Email(message = "Не корректная почта")
    @NotEmpty(message = "Введите вашу почту")
    private String email;

    @NotEmpty(message = "Введите пароль")
    private String password;

    private LocalDate dateOfBirth;
}

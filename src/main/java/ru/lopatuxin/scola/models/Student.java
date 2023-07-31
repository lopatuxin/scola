package ru.lopatuxin.scola.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author Антон
 * Модель данных ученика
 * @field role Данное поле содержит роль юзера в приложении*/
@Entity
@Table(name = "Student")
@Data
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @NotEmpty(message = "Введите имя")
    @Size(min = 3, max = 50, message = "Имя должно быть не меньше 3 символов и не больше 50")
    private String name;

    @Column(name = "surname")
    @NotEmpty(message = "Введите фамилию")
    @Size(min = 3, max = 100, message = "Фамилия должна быть не меньше 3 символов и не больше 100")
    private String surname;

    @EqualsAndHashCode.Include
    @Email(message = "Не корректная почта")
    @NotEmpty(message = "Введите вашу почту")
    private String email;

    @NotEmpty(message = "Введите пароль")
    private String password;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String role;

    @Column(name = "date_of_registration")
    private LocalDate dateOfRegistration;

    @Transient
    private long age;
}

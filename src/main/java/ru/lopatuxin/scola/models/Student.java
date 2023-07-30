package ru.lopatuxin.scola.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

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
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Введите имя")
    @Size(min = 3, max = 50, message = "Имя должно быть не меньше 3 символов и не больше 50")
    private String name;

    @Column(name = "surname")
    @NotEmpty(message = "Введите фамилию")
    @Size(min = 3, max = 100, message = "Фамилия должна быть не меньше 3 символов и не больше 100")
    private String surname;

    @EqualsAndHashCode.Include
    @Column(name = "email")
    @Email(message = "Не корректная почта")
    @NotEmpty(message = "Введите вашу почту")
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "Введите пароль")
    private String password;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "role")
    private String role;

    @Column(name = "date_of_registration")
    private LocalDate dateOfRegistration;

    @Transient
    private long age;
}

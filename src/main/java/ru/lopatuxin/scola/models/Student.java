package ru.lopatuxin.scola.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

/**
 * @author Антон
 * Модель данных ученика
 * @field role Данное поле содержит роль юзера в приложении
 * @field age Возраст необходим для отображения его на страницу данных студента*/
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @Past(message = "Введите корректную дату рождения")
    @NotNull(message = "Введите дату рождения")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "date_of_registration")
    private LocalDate dateOfRegistration;

    @Transient
    private long age;

    public Student(String name, String surname, String email, String password, @NotNull LocalDate dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }
}

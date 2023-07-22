package ru.lopatuxin.scola.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * @author Антон
 * Модель данных ученика*/
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

    @EqualsAndHashCode.Include
    @Column(name = "email")
    @Email(message = "Не корректная почта")
    @NotEmpty(message = "Введите вашу почту")
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "Введите пароль")
    private String password;

    @Column(name = "date_of_birth")
    @NotEmpty(message = "Ведите дату рождения")
    private String dateOfBirth;
}

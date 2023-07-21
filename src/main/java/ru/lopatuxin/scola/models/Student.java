package ru.lopatuxin.scola.models;

import javax.persistence.*;
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
    private String name;

    @EqualsAndHashCode.Include
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
}

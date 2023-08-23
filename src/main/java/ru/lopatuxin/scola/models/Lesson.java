package ru.lopatuxin.scola.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "lesson")
@AllArgsConstructor
public class Lesson {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "block_id", referencedColumnName = "id")
    private Block block;

    public Lesson(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

package ru.lopatuxin.scola.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Block {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "block", cascade = CascadeType.REMOVE)
    private List<Lesson> lessons;

    public Block(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Block(int id) {
        this.id = id;
    }
}

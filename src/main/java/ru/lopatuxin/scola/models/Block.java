package ru.lopatuxin.scola.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Block {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL)
    private List<Lesson> lessons;

    public Block(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Block(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
        lesson.setBlock(this);
    }
}

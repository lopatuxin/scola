package ru.lopatuxin.scola.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {

    private int id;
    private String name;
    private String description;
    private int blockId;
}

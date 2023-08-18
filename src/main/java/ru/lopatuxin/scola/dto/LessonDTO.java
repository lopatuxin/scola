package ru.lopatuxin.scola.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LessonDTO {

    private String name;
    private String description;
    private int blockId;
}

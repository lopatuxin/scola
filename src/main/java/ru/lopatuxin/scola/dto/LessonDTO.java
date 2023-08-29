package ru.lopatuxin.scola.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonDTO {

    private int id;
    private String name;
    private String description;
    private int blockId;
}

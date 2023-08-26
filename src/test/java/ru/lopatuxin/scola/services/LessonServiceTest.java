package ru.lopatuxin.scola.services;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.lopatuxin.scola.dto.LessonDTO;
import ru.lopatuxin.scola.models.Block;
import ru.lopatuxin.scola.models.Lesson;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class LessonServiceTest {

    private final LessonService lessonService;
    private Lesson lesson;

    @AfterEach
    public void clear() {
        lessonService.findAll()
                .forEach(lesson -> lessonService.deleteById(lesson.getId()));
    }

    @BeforeEach
    public void init() {
        lesson = lessonService.save(new Lesson("Test", "TestTest"));

    }

    @Test
    void findById() {
        assertEquals(lesson, lessonService.findById(lesson.getId()).get());
    }

    @Test
    void save() {
        Lesson lesson = new Lesson("Test1", "TestTest");
        Lesson exp = lessonService.save(lesson);
        assertEquals(exp, lesson);
    }

    @Test
    void findAll() {
        assertThat(lessonService.findAll()).isNotEmpty();
    }

    @Test
    void deleteById() {
        lessonService.deleteById(lesson.getId());
        assertThat(lessonService.findAll()).isEmpty();
    }

    @Test
    void convertToLesson() {
        LessonDTO lessonDTO = new LessonDTO(1, "Test", "Test", 1);
        Lesson lesson1 = new Lesson(1, "Test", "Test", new Block(1, "TEst", "Test"));
        assertEquals(lesson1, lessonService.convertToLesson(lessonDTO));
    }
}
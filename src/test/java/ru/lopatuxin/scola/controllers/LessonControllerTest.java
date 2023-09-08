package ru.lopatuxin.scola.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.lopatuxin.scola.config.DataSourceConfig;
import ru.lopatuxin.scola.dto.LessonDTO;
import ru.lopatuxin.scola.models.Block;
import ru.lopatuxin.scola.models.Lesson;
import ru.lopatuxin.scola.services.BlockService;
import ru.lopatuxin.scola.services.LessonService;
import ru.lopatuxin.scola.services.StudentService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LessonController.class)
@ActiveProfiles("test")
@WithMockUser(authorities = {"ADMIN", "USER"})
@Import(DataSourceConfig.class)
class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlockService blockService;

    @MockBean
    private LessonService lessonService;

    @MockBean
    private StudentService studentService;

    @Test
    void getCreationPage() throws Exception {
        List<Block> blocks = Arrays.asList(
                Block.builder()
                        .name("Test1")
                        .description("Test1")
                        .build(),
                Block.builder()
                        .name("Test1")
                        .name("Test1")
                        .build()
        );
        when(blockService.findAll()).thenReturn(blocks);
        mockMvc.perform(get("/lesson/create"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/createLesson"))
                .andExpect(model().attribute("blocks", blocks));
    }

    @Test
    void createLesson() throws Exception {
        LessonDTO lessonDTO =
                LessonDTO.builder()
                        .blockId(1)
                        .name("Test")
                        .description("Test")
                        .build();
        mockMvc.perform(post("/lesson/create")
                        .flashAttr("lesson", lessonDTO)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"));
        Lesson lesson = verify(lessonService).convertToLesson(lessonDTO);
        verify(lessonService).save(lesson);
    }

    @Test
    void getAllLessons() throws Exception {
        List<Lesson> lessons = Arrays.asList(
                Lesson.builder()
                        .block(Block.builder()
                                .name("Test").build())
                        .name("Test")
                        .build(),
                Lesson.builder()
                        .block(Block.builder()
                                .name("Test1").build())
                        .name("Test1")
                        .build()
        );
        when(lessonService.findAllWithBlock()).thenReturn(lessons);
        mockMvc.perform(get("/lesson/lessons"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/lessons"))
                .andExpect(model().attribute("lessons", lessons));
    }

    @Test
    void getEditPageLesson() throws Exception {
        Lesson lesson = Lesson.builder()
                .id(1)
                .block(Block.builder()
                        .name("Test")
                        .id(1)
                        .build())
                .name("Test")
                .build();
        when(lessonService.findById(1)).thenReturn(Optional.of(lesson));
        mockMvc.perform(get("/lesson/edit/{id}", 1)
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/editLesson"))
                .andExpect(model().attribute("lesson", lesson));
    }

    @Test
    void deleteLesson() throws Exception {
        mockMvc.perform(get("/lesson/delete/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"));
        verify(lessonService).deleteById(1);
    }
}
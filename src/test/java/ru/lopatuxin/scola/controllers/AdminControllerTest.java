package ru.lopatuxin.scola.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.lopatuxin.scola.models.Block;
import ru.lopatuxin.scola.services.BlockService;
import ru.lopatuxin.scola.services.LessonService;
import ru.lopatuxin.scola.services.StudentService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WithMockUser(authorities = {"ADMIN", "USER"})
@ActiveProfiles("test")
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    private BlockService blockService;

    @MockBean
    private LessonService lessonService;

    @Test
    void getAdminPage() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/admin"));
    }

    @Test
    void getStudents() throws Exception {
        mockMvc.perform(get("/admin/students"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/students"));
    }

    @Test
    void getStudentsWithSubscription() throws Exception {
        mockMvc.perform(get("/admin/students/subscription"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/students"));
    }

    @Test
    void getBlocks() throws Exception {
        mockMvc.perform(get("/admin/blocks"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/blocks"));
    }

    @Test
    void getCreateBlockPage() throws Exception {
        mockMvc.perform(get("/admin/blocks/create"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/createBlock"));
    }

    @Test
    void createBlock() throws Exception {
        mockMvc.perform(post("/admin/blocks/create")
                        .with(csrf())
                        .param("name", "Test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"));
        ArgumentCaptor<Block> argumentCaptor = ArgumentCaptor.forClass(Block.class);
        verify(blockService).create(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue().getName(), "Test");
    }

    @Test
    void getEditPageBlock() throws Exception {
        Block block = new Block(1, "Test", "Test");
        Mockito.when(blockService.findById(Mockito.anyInt())).thenReturn(Optional.of(block));
        mockMvc.perform(get("/admin/blocks/edit/{id}", 1)
                .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/editBlock"));
    }

    @Test
    void editBlock() throws Exception {
        Block block = new Block(1, "Test", "Test");
        Mockito.when(blockService.findById(Mockito.anyInt())).thenReturn(Optional.of(block));
        mockMvc.perform(post("/admin/blocks/edit/{id}", 1)
                        .with(csrf())
                        .param("name", "Test2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"));
        ArgumentCaptor<Block> argumentCaptor = ArgumentCaptor.forClass(Block.class);
        verify(blockService).create(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue().getName(), "Test2");
    }
}
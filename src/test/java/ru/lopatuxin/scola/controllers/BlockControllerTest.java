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
import ru.lopatuxin.scola.models.Block;
import ru.lopatuxin.scola.services.BlockService;
import ru.lopatuxin.scola.services.StudentService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BlockController.class)
@ActiveProfiles("test")
@WithMockUser(authorities = {"ADMIN", "USER"})
@Import(DataSourceConfig.class)
class BlockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlockService blockService;

    @MockBean
    private StudentService studentService;

    @Test
    void getBlocksPage() throws Exception {
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
        mockMvc.perform(get("/block/blocks"))
                .andExpect(status().isOk())
                .andExpect(view().name("block/blocks"))
                .andExpect(model().attribute("blocks", blocks));
        verify(blockService).findAll();
    }

    @Test
    void getBlockPage() throws Exception {
        Block block = Block.builder()
                .id(1)
                .name("Test")
                .description("Test")
                .build();
        when(blockService.findById(1)).thenReturn(Optional.of(block));
        mockMvc.perform(get("/block/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("block/block"))
                .andExpect(model().attributeExists("block", "lessons"));
        verify(blockService).findById(1);
        verify(blockService).findAllLessonByBlockId(1);
    }

    @Test
    void getBlocksOnAdminPage() throws Exception {
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
        mockMvc.perform(get("/block/admin/blocks"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/blocks"))
                .andExpect(model().attribute("blocks", blocks));
    }

    @Test
    void getCreationBlockPage() throws Exception {
        mockMvc.perform(get("/block/blocks/create"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/createBlock"));
    }

    @Test
    void createBlock() throws Exception {
        Block block = Block.builder()
                .name("Test")
                .description("Test")
                .build();
        mockMvc.perform(post("/block/blocks/create")
                        .with(csrf())
                        .param("name", block.getName())
                        .param("description", block.getDescription()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"));
        verify(blockService).create(block);
    }

    @Test
    void getEditPageBlock() throws Exception {
        Block block = Block.builder()
                .id(1)
                .name("Test")
                .description("Test")
                .build();
        when(blockService.findById(1)).thenReturn(Optional.of(block));
        mockMvc.perform(get("/block/blocks/edit/{id}", 1)
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/editBlock"))
                .andExpect(model().attribute("block", block));
    }

    @Test
    void editBlock() throws Exception {
        Block block = Block.builder()
                .name("Test")
                .description("Test")
                .build();
        mockMvc.perform(post("/block/blocks/edit")
                        .with(csrf())
                        .param("name", block.getName())
                        .param("description", block.getDescription()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"));
        verify(blockService).create(block);
    }

    @Test
    void deleteBlock() throws Exception {
        mockMvc.perform(get("/block/delete/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"));
        verify(blockService).delete(1);
    }
}
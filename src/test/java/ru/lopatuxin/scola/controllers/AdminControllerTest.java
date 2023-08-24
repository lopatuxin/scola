package ru.lopatuxin.scola.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
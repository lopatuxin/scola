package ru.lopatuxin.scola.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.lopatuxin.scola.config.DataSourceConfig;
import ru.lopatuxin.scola.models.Role;
import ru.lopatuxin.scola.models.Student;
import ru.lopatuxin.scola.services.StudentService;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
@WithMockUser(authorities = {"ADMIN", "USER"})
@Import(DataSourceConfig.class)
@ExtendWith(SpringExtension.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    void getStudents() throws Exception {
        when(studentService.findAll()).thenReturn(Collections.singletonList(new Student()));
        mockMvc.perform(get("/student/students"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/students"))
                .andExpect(model().attributeExists("students"));
        verify(studentService).findAll();
    }

    @Test
    void getStudentsWithSubscription() throws Exception {
        when(studentService.findByRole(Role.USER)).thenReturn(Collections.singletonList(new Student()));
        mockMvc.perform(get("/student/students/subscription"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/students"))
                .andExpect(model().attributeExists("students"));
        verify(studentService).findByRole(Role.USER);
    }

    @Test
    void getStudent() throws Exception {
        int studentId = 1;
        Student student = new Student();
        when(studentService.findById(studentId)).thenReturn(Optional.of(student));
        mockMvc.perform(get("/student/{id}", studentId))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/student"))
                .andExpect(model().attributeExists("student"));
        verify(studentService).findById(studentId);
    }

    @Test
    void getUpdateStudentPage() throws Exception {
        int studentId = 1;
        Student student = new Student();
        when(studentService.findById(studentId)).thenReturn(Optional.of(student));
        mockMvc.perform(get("/student/update/{id}", studentId))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/editStudent"))
                .andExpect(model().attributeExists("student"));
        verify(studentService).findById(studentId);
    }

    @Test
    void updateStudent() throws Exception {
        int studentId = 1;
        mockMvc.perform(post("/student/update")
                        .with(csrf())
                        .param("id", String.valueOf(studentId))
                        .param("role", Role.ADMIN.name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
        verify(studentService).updateStudentByRole(Role.ADMIN.name(), studentId);
    }

    @Test
    void testGetUpdateStudentPageNotFound() throws Exception {
        int studentId = 1;
        when(studentService.findById(studentId)).thenReturn(Optional.empty());
        mockMvc.perform(get("/student/update/{id}", studentId))
                .andExpect(status().isOk())
                .andExpect(view().name("errors/404"))
                .andExpect(model().attributeExists("error"));
        verify(studentService).findById(studentId);
    }
}
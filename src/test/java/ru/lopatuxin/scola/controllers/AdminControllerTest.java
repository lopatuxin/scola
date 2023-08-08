package ru.lopatuxin.scola.controllers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.lopatuxin.scola.ScolaApplication;
import ru.lopatuxin.scola.models.Student;
import ru.lopatuxin.scola.repositories.StudentRepository;
import ru.lopatuxin.scola.security.StudentDetails;
import ru.lopatuxin.scola.services.StudentDetailService;
import ru.lopatuxin.scola.services.StudentService;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc
@SpringBootTest(classes = ScolaApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;
    @MockBean
    private StudentDetailService studentDetailService;
    @MockBean
    private StudentRepository studentRepository;

    @BeforeAll
    void init() {
        studentService.save(new Student(
                "test",
                "test",
                "test@mail.ru",
                "test",
                LocalDate.of(2020, 2, 2)
        ));
    }

    @AfterAll
    void clear() {
        List<Student> students = studentService.findAll();
        for (Student student : students) {
            studentService.delete(student);
        }
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAdminPage() throws Exception {
        mockMvc.perform(get("/admin")
                        .requestAttr("user", new StudentDetails(new Student(
                                "test",
                                "test",
                                "test@mail.ru",
                                "test",
                                LocalDate.of(2020, 2, 2)
                        ))));
    }

    @Test
    void getStudents() {
    }

    @Test
    void getStudentsWithSubscription() {
    }
}
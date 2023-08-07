package ru.lopatuxin.scola.services;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.lopatuxin.scola.models.Student;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentServiceTest {

    private final StudentService studentService;
    private Student student;

    @BeforeAll
    public void init () {
        student =
                new Student(
                        "Anton",
                        "Lopatuxin",
                        "test@mail.ru",
                        "test",
                        LocalDate.of(1989, 4, 3));
    }

    @BeforeAll
    public void clear() {
        List<Student> students = studentService.findAll();
        for (Student student : students) {
            studentService.delete(student);
        }
    }

    @Test
    void save() {
        studentService.save(student);
        assertThat(student).isEqualTo(studentService.findByEmail(student.getEmail()).get());
    }

    @Test
    void whenSaveStudentAddRoleGuest() {
        Student expected = studentService.findByEmail(student.getEmail()).get();
        assertThat("ROLE_GUEST").isEqualTo(expected.getRole().toString());
    }

    @Test
    void whenSaveStudentAddDateRegistration() {
        Student expected = studentService.findByEmail(student.getEmail()).get();
        assertThat(expected.getDateOfRegistration()).isNotNull();
    }

    @Test
    void delete() {
        studentService.delete(student);
        assertThat(studentService.findByEmail(student.getEmail())).isEmpty();
    }
}
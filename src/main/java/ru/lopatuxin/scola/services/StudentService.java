package ru.lopatuxin.scola.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.lopatuxin.scola.models.Role;
import ru.lopatuxin.scola.models.Student;
import ru.lopatuxin.scola.repositories.StudentRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StudentService(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void save(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setRole(Role.GUEST);
        student.setDateOfRegistration(LocalDate.now());
        studentRepository.save(student);
    }

    public Optional<Student> findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    /**
     * Данный метод перед тем как выдать список студентов задает им возраст*/
    public List<Student> findAll() {
        return studentRepository.findAll().stream()
                .peek(student -> student.setAge(getYear(student.getDateOfBirth())))
                .collect(Collectors.toList());
    }

    public List<Student> findByRole(Role role) {
        List<Student> students = studentRepository.findAllByRole(role);
        for (Student student : students) {
            student.setAge(getYear(student.getDateOfBirth()));
        }
        return students;
    }

    public Optional<Student> findById(int id) {
        Optional<Student> student = studentRepository.findById(id);
        student.get().setAge(getYear(student.get().getDateOfBirth()));
        return student;
    }

    @Transactional
    public void delete(Student student) {
        studentRepository.delete(student);
    }

    @Transactional
    public void updateStudentByRole(String role, int id) {
        studentRepository.updateStudentByRole(role, id);
    }

    /**
     * Данный метод принимает дату рождения и высчитывает разницу между сегодняшней датой в годах*/
    private long getYear(LocalDate dateOfBirth) {
        return ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now());
    }
}

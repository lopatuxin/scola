package ru.lopatuxin.scola.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.lopatuxin.scola.dto.StudentDTO;
import ru.lopatuxin.scola.models.Role;
import ru.lopatuxin.scola.models.Student;
import ru.lopatuxin.scola.repositories.StudentRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    /**
     * Сохраняет студента, хеширует пароль, устанавливает роль "GUEST", дату регистрации и уровень.
     *
     * @param student Сущность студента для сохранения.
     */
    @Transactional
    public void save(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setRole(Role.GUEST);
        student.setDateOfRegistration(LocalDate.now());
        student.setLevel(0);
        studentRepository.save(student);
    }

    /**
     * Находит студента по адресу электронной почты.
     *
     * @param email Адрес электронной почты для поиска.
     * @return Optional, содержащий студента, если он найден.
     */
    public Optional<Student> findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    /**
     * Находит всех студентов и вычисляет их возраст перед возвратом.
     *
     * @return Список всех студентов с вычисленным возрастом.
     */
    public List<Student> findAll() {
        List<Student> students = studentRepository.findAll();
        students.forEach(student -> student.setAge(getYear(student.getDateOfBirth())));
        return students;
    }

    /**
     * Находит студентов по роли и вычисляет их возраст перед возвратом.
     *
     * @param role Роль студентов для поиска.
     * @return Список студентов с указанной ролью с вычисленным возрастом.
     */
    public List<Student> findByRole(Role role) {
        List<Student> students = studentRepository.findAllByRole(role);
        students.forEach(student -> student.setAge(getYear(student.getDateOfBirth())));
        return students;
    }

    /**
     * Находит студента по идентификатору и вычисляет его возраст перед возвратом.
     *
     * @param id Идентификатор студента.
     * @return Optional, содержащий студента с вычисленным возрастом, если он найден.
     */
    public Optional<Student> findById(int id) {
        Optional<Student> student = studentRepository.findById(id);
        student.ifPresent(value -> value.setAge(getYear(value.getDateOfBirth())));
        return student;
    }

    /**
     * Удаляет студента.
     *
     * @param student Сущность студента для удаления.
     */
    @Transactional
    public void delete(Student student) {
        studentRepository.delete(student);
    }

    /**
     * Обновляет роль студента по идентификатору.
     *
     * @param role Новая роль для обновления.
     * @param id   Идентификатор студента.
     */
    @Transactional
    public void updateStudentByRole(String role, int id) {
        studentRepository.updateStudentByRole(role, id);
    }

    /**
     * Вычисляет возраст на основе даты рождения и текущей даты.
     *
     * @param dateOfBirth Дата рождения студента.
     * @return Возраст студента.
     */
    private long getYear(LocalDate dateOfBirth) {
        return ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now());
    }

    /**
     * Преобразует DTO студента в сущность студента.
     *
     * @param studentDTO DTO студента для преобразования.
     * @return Сущность студента.
     */
    public Student convertToStudent(StudentDTO studentDTO) {
        return modelMapper.map(studentDTO, Student.class);
    }
}

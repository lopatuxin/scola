package ru.lopatuxin.scola.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.lopatuxin.scola.models.Student;
import ru.lopatuxin.scola.repositories.StudentRepository;

import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        student.setRole("ROLE_USER");
        student.setDateOfRegistration(new Date());
        studentRepository.save(student);
    }

    public Optional<Student> findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public List<Student> findAll() {
        List<Student> students = studentRepository.findAll();
        for (Student student : students) {
            student.setAge(getYear(student.getDateOfBirth()));
        }
        return students;
    }

    /**
     * Данный метод принимает дату рождения и высчитывает из миллисекунд годы по формуле*/
    private int getYear(Date dateOfBirth) {
        Date nowDate = new Date();
        long timeMill = nowDate.getTime() - dateOfBirth.getTime();
        return (int) ((((timeMill / 1000) / 60) / 60) / 24) / 365;
    }
}

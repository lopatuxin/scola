package ru.lopatuxin.scola.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.lopatuxin.scola.dto.StudentDTO;
import ru.lopatuxin.scola.models.Student;
import ru.lopatuxin.scola.services.StudentService;

@Component
public class StudentValidator implements Validator {
    private final StudentService studentService;

    @Autowired
    public StudentValidator(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Student.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StudentDTO student = (StudentDTO) target;
        if (studentService.findByEmail(student.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "Пользователь с такой почтой уже существует");
        }
    }
}

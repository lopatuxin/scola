package ru.lopatuxin.scola.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lopatuxin.scola.models.Student;
import ru.lopatuxin.scola.services.StudentService;
import ru.lopatuxin.scola.util.StudentValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final StudentService studentService;
    private final StudentValidator studentValidator;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(StudentService studentService, StudentValidator studentValidator, PasswordEncoder passwordEncoder) {
        this.studentService = studentService;
        this.studentValidator = studentValidator;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String getRegistrationPage(@ModelAttribute("student") Student student) {
        return "register/register";
    }

    @PostMapping()
    public String registration(@ModelAttribute("student") @Valid Student student,
                               BindingResult bindingResult) {
        studentValidator.validate(student, bindingResult);
        if (bindingResult.hasErrors()) {
            return "register/register";
        }
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        studentService.save(student);
        return "auth/login";
    }
}

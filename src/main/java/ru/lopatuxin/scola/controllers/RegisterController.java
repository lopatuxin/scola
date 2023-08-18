package ru.lopatuxin.scola.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lopatuxin.scola.dto.StudentDTO;
import ru.lopatuxin.scola.services.StudentService;
import ru.lopatuxin.scola.util.StudentValidator;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final StudentService studentService;
    private final StudentValidator studentValidator;

    @Autowired
    public RegisterController(StudentService studentService,
                              StudentValidator studentValidator) {
        this.studentService = studentService;
        this.studentValidator = studentValidator;
    }

    @GetMapping()
    public String getRegistrationPage(@ModelAttribute("student") StudentDTO studentDTO) {
        return "register/register";
    }

    @PostMapping()
    public String registration(@ModelAttribute("student") @Valid StudentDTO studentDTO,
                               BindingResult bindingResult) {
        studentValidator.validate(studentDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "register/register";
        }
        studentService.save(studentService.convertToStudent(studentDTO));
        return "auth/login";
    }

}

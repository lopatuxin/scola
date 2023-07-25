package ru.lopatuxin.scola.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lopatuxin.scola.dto.StudentDTO;
import ru.lopatuxin.scola.models.Student;
import ru.lopatuxin.scola.services.StudentService;
import ru.lopatuxin.scola.util.StudentValidator;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final StudentService studentService;
    private final StudentValidator studentValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public RegisterController(StudentService studentService, StudentValidator studentValidator, ModelMapper modelMapper) {
        this.studentService = studentService;
        this.studentValidator = studentValidator;
        this.modelMapper = modelMapper;
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
        studentService.save(convertToStudent(studentDTO));
        return "auth/login";
    }

    private Student convertToStudent(StudentDTO studentDTO) {
        return modelMapper.map(studentDTO, Student.class);
    }
}

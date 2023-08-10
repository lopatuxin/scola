package ru.lopatuxin.scola.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lopatuxin.scola.models.Block;
import ru.lopatuxin.scola.models.Role;
import ru.lopatuxin.scola.models.Student;
import ru.lopatuxin.scola.services.StudentService;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final StudentService studentService;

    public AdminController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ModelAttribute("user")
    public String getUser(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<Student> student = studentService.findByEmail(userDetails.getUsername());
        return student.map(value -> value.getName() + " " + value.getSurname()).orElse("User");
    }

    @GetMapping()
    public String getAdminPage() {
        return "admin/admin";
    }

    @GetMapping("/students")
    public String getStudents(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "admin/students";
    }

    @GetMapping("/students/subscription")
    public String getStudentsWithSubscription(Model model) {
        model.addAttribute("students", studentService.findByRole(Role.USER));
        return "admin/students";
    }

    @GetMapping("/students/{id}")
    public String getStudent(@PathVariable int id, Model model) {
        model.addAttribute("student", studentService.findById(id).get());
        return "admin/student";
    }

    @GetMapping("/blocks/create")
    public String createBlock(@ModelAttribute("block")Block block) {
        return "admin/createBlock";
    }
}

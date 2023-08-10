package ru.lopatuxin.scola.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lopatuxin.scola.models.Block;
import ru.lopatuxin.scola.security.StudentDetails;
import ru.lopatuxin.scola.services.StudentDetailService;
import ru.lopatuxin.scola.services.StudentService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final StudentService studentService;
    private final StudentDetailService studentDetailService;

    public AdminController(StudentService studentService, StudentDetailService studentDetailService) {
        this.studentService = studentService;
        this.studentDetailService = studentDetailService;
    }

    @ModelAttribute("user")
    public StudentDetails getUser(HttpServletRequest request) {
        return studentDetailService.getUser(request);
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
        model.addAttribute("students", studentService.findByRole("ROLE_USER"));
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

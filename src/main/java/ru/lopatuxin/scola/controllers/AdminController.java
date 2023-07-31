package ru.lopatuxin.scola.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping()
    public String getAdminPage(Model model, HttpServletRequest request) {
        model.addAttribute("user", studentDetailService.getUser(request));
        return "admin/admin";
    }

    @GetMapping("/students")
    public String getStudents(Model model, HttpServletRequest request) {
        model.addAttribute("user", studentDetailService.getUser(request));
        model.addAttribute("students", studentService.findAll());
        return "admin/students";
    }

    @GetMapping("/subscription")
    public String getStudentsWithSubscription(Model model, HttpServletRequest request) {
        model.addAttribute("user", studentDetailService.getUser(request));
        model.addAttribute("students", studentService.findByRole("ROLE_USER"));
        return "admin/students";
    }
}

package ru.lopatuxin.scola.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lopatuxin.scola.models.Role;
import ru.lopatuxin.scola.models.Student;
import ru.lopatuxin.scola.services.StudentService;

import java.util.Optional;

/**
 * Контроллер для управления студентами в административной части приложения.
 * Предоставляет методы для просмотра, редактирования и обновления данных студентов.
 * @author Антон
 */
@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    /**
     * Получение имени текущего пользователя и добавление его в модель.
     *
     * @param userDetails Информация о текущем пользователе.
     * @return Имя текущего пользователя или "User", если пользователь не найден.
     */
    @ModelAttribute("user")
    public String getUser(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<Student> student = studentService.findByEmail(userDetails.getUsername());
        return student.map(value -> value.getName() + " " + value.getSurname()).orElse("User");
    }

    /**
     * Отображение страницы со списком всех студентов.
     *
     * @param model Модель, используемая для передачи данных в представление.
     * @return Имя представления для отображения списка студентов.
     */
    @GetMapping("/students")
    public String getStudents(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "admin/students";
    }

    /**
     * Отображение страницы со списком студентов, имеющих подписку.
     *
     * @param model Модель, используемая для передачи данных в представление.
     * @return Имя представления для отображения списка студентов с подпиской.
     */
    @GetMapping("/students/subscription")
    public String getStudentsWithSubscription(Model model) {
        model.addAttribute("students", studentService.findByRole(Role.USER));
        return "admin/students";
    }

    /**
     * Отображение страницы с информацией о конкретном студенте.
     *
     * @param id    Идентификатор студента.
     * @param model Модель, используемая для передачи данных в представление.
     * @return Имя представления для отображения информации о студенте.
     */
    @GetMapping("/{id}")
    public String getStudent(@PathVariable int id, Model model) {
        var student = studentService.findById(id);
        if (student.isEmpty()) {
            model.addAttribute("error", "Not Found");
            return "errors/404";
        }
        model.addAttribute("student", student.get());
        return "admin/student";
    }

    /**
     * Отображение страницы редактирования данных студента.
     *
     * @param id    Идентификатор студента.
     * @param model Модель, используемая для передачи данных в представление.
     * @return Имя представления для редактирования данных студента.
     */
    @GetMapping("/update/{id}")
    public String getUpdateStudentPage(@PathVariable("id") int id, Model model) {
        var student = studentService.findById(id);
        if (student.isEmpty()) {
            model.addAttribute("error", "Not Found");
            return "errors/404";
        }
        model.addAttribute("student", student.get());
        return "admin/editStudent";
    }

    /**
     * Обновление роли студента.
     *
     * @param id    Идентификатор студента.
     * @param role  Новая роль студента.
     * @return Перенаправление на страницу списка студентов после обновления роли.
     */
    @PostMapping("/update")
    public String updateStudent(@RequestParam("id") int id, @RequestParam("role") String role) {
        studentService.updateStudentByRole(role, id);
        return "redirect:/admin";
    }
}

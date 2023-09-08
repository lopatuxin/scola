package ru.lopatuxin.scola.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lopatuxin.scola.models.Student;
import ru.lopatuxin.scola.services.StudentService;

import java.util.Optional;

/**
 * Контроллер, предоставляющий административные операции и отображения.
 * Данный контроллер обеспечивает отображение административной страницы и получение
 * информации о текущем аутентифицированном пользователе.
 * URL-префикс для этого контроллера: "/admin".
 * @author Антон
 */
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final StudentService studentService;

    /**
     * Получение имени текущего пользователя и добавление его в модель.
     * Используется для отображения имени пользователя на административных страницах.
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
     * Отображение административной главной страницы.
     *
     * @return Имя представления для отображения административной главной страницы.
     */
    @GetMapping()
    public String getAdminPage() {
        return "admin/admin";
    }
}

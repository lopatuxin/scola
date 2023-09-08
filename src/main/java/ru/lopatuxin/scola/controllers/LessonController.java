package ru.lopatuxin.scola.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lopatuxin.scola.dto.LessonDTO;
import ru.lopatuxin.scola.models.Block;
import ru.lopatuxin.scola.models.Student;
import ru.lopatuxin.scola.services.BlockService;
import ru.lopatuxin.scola.services.LessonService;
import ru.lopatuxin.scola.services.StudentService;

import java.util.Optional;

/**
 * Контроллер для управления уроками и связанными операциями.
 * Этот контроллер предоставляет методы для создания, отображения, редактирования и удаления уроков.
 * URL-префикс для этого контроллера: "/lesson".
 * @author Антон
 */
@Controller
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    private final StudentService studentService;

    private final BlockService blockService;

    /**
     * Получение имени текущего пользователя и добавление его в модель.
     * Используется для отображения имени пользователя на страницах уроков.
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
     * Отображение страницы создания нового урока.
     *
     * @param lessonDTO Объект DTO для урока.
     * @param model     Модель, используемая для передачи данных в представление.
     * @param block     Объект блока обучения для передачи в представление.
     * @return Имя представления для создания урока.
     */
    @GetMapping("/create")
    public String getCreationPage(@ModelAttribute("lesson") LessonDTO lessonDTO, Model model,
                                  @ModelAttribute("block") Block block) {
        model.addAttribute("blocks", blockService.findAll());
        return "admin/createLesson";
    }

    /**
     * Обработка POST-запроса для создания нового урока.
     *
     * @param lessonDTO Объект DTO для урока, переданный из формы.
     * @return Перенаправление на административную страницу после создания урока.
     */
    @PostMapping("/create")
    public String createLesson(@ModelAttribute("lesson")LessonDTO lessonDTO) {
        lessonService.save(lessonService.convertToLesson(lessonDTO));
        return "redirect:/admin";
    }

    /**
     * Отображение страницы со списком всех уроков.
     *
     * @param model Модель, используемая для передачи данных в представление.
     * @return Имя представления для отображения списка уроков.
     */
    @GetMapping("/lessons")
    public String getAllLessons(Model model) {
        model.addAttribute("lessons", lessonService.findAllWithBlock());
        return "admin/lessons";
    }

    /**
     * Отображение страницы редактирования урока.
     *
     * @param id    Идентификатор урока, который требуется редактировать.
     * @param model Модель, используемая для передачи данных в представление.
     * @return Имя представления для редактирования урока.
     */
    @GetMapping("/edit/{id}")
    public String getEditPageLesson(@PathVariable("id") int id, Model model) {
        var lesson = lessonService.findById(id);
        if (lesson.isEmpty()) {
            model.addAttribute("error", "Not Found");
            return "errors/404";
        }
        model.addAttribute("lesson", lesson.get());
        model.addAttribute("blocks", blockService.findAll());
        return "admin/editLesson";
    }

    /**
     * Удаление урока по его идентификатору.
     *
     * @param id Идентификатор урока, который требуется удалить.
     * @return Перенаправление на административную страницу после удаления урока.
     */
    @GetMapping("/delete/{id}")
    public String deleteLesson(@PathVariable("id") int id) {
        lessonService.deleteById(id);
        return "redirect:/admin";
    }
}

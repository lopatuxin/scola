package ru.lopatuxin.scola.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lopatuxin.scola.models.Block;
import ru.lopatuxin.scola.models.Student;
import ru.lopatuxin.scola.services.BlockService;
import ru.lopatuxin.scola.services.StudentService;

import java.util.Optional;

/**
 * Контроллер для управления блоками обучения и связанными операциями.
 * Этот контроллер предоставляет методы для отображения информации о блоках,
 * создания, редактирования и удаления блоков.
 * URL-префикс для этого контроллера: "/block".
 * @author Антон
 */
@Controller
@RequestMapping("/block")
public class BlockController {

    private final BlockService blockService;
    private final StudentService studentService;

    @Autowired
    public BlockController(BlockService blockService, StudentService studentService) {
        this.blockService = blockService;
        this.studentService = studentService;
    }

    /**
     * Получение имени текущего пользователя и добавление его в модель.
     * Используется для отображения имени пользователя на страницах блоков.
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
     * Отображение страницы со списком всех блоков обучения на странице юзера.
     *
     * @param model Модель, используемая для передачи данных в представление.
     * @return Имя представления для отображения списка блоков.
     */
    @GetMapping("/blocks")
    public String getBlocksPage(Model model) {
        model.addAttribute("blocks", blockService.findAll());
        return "block/blocks";
    }

    /**
     * Отображение страницы с информацией о конкретном блоке обучения на странице юзера.
     * Также отображает связанные с блоком уроки.
     *
     * @param id    Идентификатор блока обучения.
     * @param model Модель, используемая для передачи данных в представление.
     * @return Имя представления для отображения информации о блоке.
     */
    @GetMapping("/{id}")
    public String getBlockPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("block", blockService.findById(id));
        model.addAttribute("lessons", blockService.findAllLessonByBlockId(id));
        return "block/block";
    }

    /**
     * Отображение страницы со списком блоков обучения для административной части приложения.
     *
     * @param model Модель, используемая для передачи данных в представление.
     * @return Имя представления для отображения списка блоков в административной части.
     */
    @GetMapping("/admin/blocks")
    public String getBlocksOnAdminPage(Model model) {
        model.addAttribute("blocks", blockService.findAll());
        return "admin/blocks";
    }

    /**
     * Отображение страницы создания нового блока обучения для административной части.
     *
     * @param block Объект блока обучения для передачи в представление.
     * @return Имя представления для создания блока обучения.
     */
    @GetMapping("/blocks/create")
    public String getCreationBlockPage(@ModelAttribute("block") Block block) {
        return "admin/createBlock";
    }

    /**
     * Обработка POST-запроса для создания нового блока обучения.
     *
     * @param block Объект блока обучения, переданный из формы.
     * @return Перенаправление на административную страницу после создания блока.
     */
    @PostMapping("/blocks/create")
    public String createBlock(@ModelAttribute("block") Block block) {
        blockService.create(block);
        return "redirect:/admin";
    }

    /**
     * Отображение страницы редактирования блока обучения.
     *
     * @param id    Идентификатор блока обучения, который требуется редактировать.
     * @param model Модель, используемая для передачи данных в представление.
     * @return Имя представления для редактирования блока обучения.
     */
    @GetMapping("/blocks/edit/{id}")
    public String getEditPageBlock(@PathVariable("id") int id, Model model) {
        var block = blockService.findById(id);
        if (block.isEmpty()) {
            model.addAttribute("error", "Not Found");
            return "errors/404";
        }
        model.addAttribute("block", block.get());
        return "admin/editBlock";
    }

    /**
     * Обработка POST-запроса для редактирования блока обучения.
     *
     * @param block Объект блока обучения, переданный из формы.
     * @return Перенаправление на административную страницу после редактирования блока.
     */
    @PostMapping("/blocks/edit")
    public String editBlock(@ModelAttribute("block") Block block) {
        blockService.create(block);
        return "redirect:/admin";
    }

    /**
     * Удаление блока обучения по его идентификатору.
     *
     * @param id Идентификатор блока обучения, который требуется удалить.
     * @return Перенаправление на административную страницу после удаления блока.
     */
    @GetMapping("/delete/{id}")
    public String deleteBlock(@PathVariable("id") int id) {
        blockService.delete(id);
        return "redirect:/admin";
    }


}

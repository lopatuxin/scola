package ru.lopatuxin.scola.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lopatuxin.scola.dto.LessonDTO;
import ru.lopatuxin.scola.models.Block;
import ru.lopatuxin.scola.models.Role;
import ru.lopatuxin.scola.models.Student;
import ru.lopatuxin.scola.services.BlockService;
import ru.lopatuxin.scola.services.LessonService;
import ru.lopatuxin.scola.services.StudentService;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final StudentService studentService;
    private final BlockService blockService;

    private final LessonService lessonService;

    public AdminController(StudentService studentService, BlockService blockService, LessonService lessonService) {
        this.studentService = studentService;
        this.blockService = blockService;
        this.lessonService = lessonService;
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
        var student = studentService.findById(id);
        if (student.isEmpty()) {
            model.addAttribute("error", "Not Found");
            return "errors/404";
        }
        model.addAttribute("student", student.get());
        return "admin/student";
    }

    @GetMapping("/students/update/{id}")
    public String getUpdateStudentPage(@PathVariable("id") int id, Model model) {
        var student = studentService.findById(id);
        if (student.isEmpty()) {
            model.addAttribute("error", "Not Found");
            return "errors/404";
        }
        model.addAttribute("student", student.get());
        return "admin/editStudent";
    }

    @PostMapping("/students/update")
    public String updateStudent(@RequestParam("id") int id, @RequestParam("role") String role) {
        studentService.updateStudentByRole(role, id);
        return "redirect:/admin";
    }

    @GetMapping("/blocks")
    public String getBlocks(Model model) {
        model.addAttribute("blocks", blockService.findAll());
        return "admin/blocks";
    }

    @GetMapping("/blocks/create")
    public String getCreateBlockPage(@ModelAttribute("block")Block block) {
        return "admin/createBlock";
    }

    @PostMapping("/blocks/create")
    public String createBlock(@ModelAttribute("block") Block block) {
        blockService.create(block);
        return "redirect:/admin";
    }

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

    @PostMapping("/blocks/edit/{id}")
    public String editBlock(@PathVariable("id") int id, @ModelAttribute("block") Block block) {
        blockService.update(id, block.getDescription(), block.getName());
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteBlock(@PathVariable("id") int id) {
        blockService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/lessons/create")
    public String getCreationPage(@ModelAttribute("lesson")LessonDTO lessonDTO, Model model,
                                  @ModelAttribute("block") Block block) {
        model.addAttribute("blocks", blockService.findAll());
        return "admin/createLesson";
    }

    @PostMapping("/lessons/create")
    public String createLesson(@ModelAttribute("lesson")LessonDTO lessonDTO) {
        lessonService.save(lessonService.convertToLesson(lessonDTO));
        return "redirect:/admin";
    }

    @GetMapping("/lessons")
    public String getAllLessons(Model model) {
        model.addAttribute("lessons", lessonService.findAllWithBlock());
        return "admin/lessons";
    }

    @GetMapping("lessons/edit/{id}")
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

    @PostMapping("/lessons/edit")
    public String editLesson(@ModelAttribute("lesson")LessonDTO lessonDTO) {
        lessonService.save(lessonService.convertToLesson(lessonDTO));
        return "redirect:/admin";
    }

    @GetMapping("/lessons/delete/{id}")
    public String deleteLesson(@PathVariable("id") int id) {
        lessonService.deleteById(id);
        return "redirect:/admin";
    }
}

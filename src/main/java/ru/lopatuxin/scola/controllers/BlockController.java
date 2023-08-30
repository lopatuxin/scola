package ru.lopatuxin.scola.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lopatuxin.scola.models.Student;
import ru.lopatuxin.scola.services.BlockService;
import ru.lopatuxin.scola.services.StudentService;

import java.util.Optional;

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

    @ModelAttribute("user")
    public String getUser(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<Student> student = studentService.findByEmail(userDetails.getUsername());
        return student.map(value -> value.getName() + " " + value.getSurname()).orElse("User");
    }

    @GetMapping("/blocks")
    public String getBlocksPage(Model model) {
        model.addAttribute("blocks", blockService.findAll());
        return "block/blocks";
    }

    @GetMapping("/{id}")
    public String getBlockPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("block", blockService.findById(id));
        return "block/block";
    }
}

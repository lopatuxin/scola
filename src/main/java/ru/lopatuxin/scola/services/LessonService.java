package ru.lopatuxin.scola.services;

import org.springframework.stereotype.Service;
import ru.lopatuxin.scola.models.Lesson;
import ru.lopatuxin.scola.repositories.LessonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public Optional<Lesson> findById(int id) {
        return lessonRepository.findById(id);
    }

    public Lesson save(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    public void deleteById(int id) {
        lessonRepository.deleteById(id);
    }
}

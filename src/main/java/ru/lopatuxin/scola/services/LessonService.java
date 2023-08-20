package ru.lopatuxin.scola.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.lopatuxin.scola.dto.LessonDTO;
import ru.lopatuxin.scola.models.Lesson;
import ru.lopatuxin.scola.repositories.LessonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;
    private final ModelMapper modelMapper;

    public LessonService(LessonRepository lessonRepository, ModelMapper modelMapper) {
        this.lessonRepository = lessonRepository;
        this.modelMapper = modelMapper;
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

    public Lesson convertToLesson(LessonDTO lessonDTO) {
        return modelMapper.map(lessonDTO, Lesson.class);
    }

    public List<Lesson> findAllWithBlock() {
        return lessonRepository.findAllWithBlock();
    }
}

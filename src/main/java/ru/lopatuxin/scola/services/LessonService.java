package ru.lopatuxin.scola.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.lopatuxin.scola.dto.LessonDTO;
import ru.lopatuxin.scola.models.Lesson;
import ru.lopatuxin.scola.repositories.LessonRepository;

import java.util.List;
import java.util.Optional;

/**
 * Класс {@code LessonService} предоставляет сервисные методы для работы с объектами {@link Lesson}.
 * @author Антон
 */
@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;

    private final ModelMapper modelMapper;

    /**
     * Находит урок по его идентификатору.
     *
     * @param id Идентификатор урока.
     * @return Опциональный объект урока (может быть пустым, если урок не найден).
     */
    public Optional<Lesson> findById(int id) {
        return lessonRepository.findById(id);
    }

    /**
     * Сохраняет урок.
     *
     * @param lesson Объект урока, который нужно сохранить.
     * @return Сохраненный урок.
     */
    public Lesson save(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    /**
     * Возвращает список всех уроков.
     *
     * @return Список всех уроков.
     */
    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    /**
     * Удаляет урок по его идентификатору.
     *
     * @param id Идентификатор урока, который нужно удалить.
     */
    public void deleteById(int id) {
        lessonRepository.deleteById(id);
    }

    /**
     * Преобразует объект DTO урока в объект урока.
     *
     * @param lessonDTO Объект DTO урока.
     * @return Объект урока.
     */
    public Lesson convertToLesson(LessonDTO lessonDTO) {
        return modelMapper.map(lessonDTO, Lesson.class);
    }

    /**
     * Возвращает список всех уроков с информацией о блоках, к которым они принадлежат.
     *
     * @return Список уроков с информацией о блоках.
     */
    public List<Lesson> findAllWithBlock() {
        return lessonRepository.findAllWithBlock();
    }
}

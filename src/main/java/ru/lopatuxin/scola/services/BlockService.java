package ru.lopatuxin.scola.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lopatuxin.scola.models.Block;
import ru.lopatuxin.scola.models.Lesson;
import ru.lopatuxin.scola.repositories.BlockRepository;

import java.util.List;
import java.util.Optional;

/**
 * Класс {@code BlockService} предоставляет сервисные методы для работы с объектами {@link Block}.
 * @author Антон
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BlockService {

    private final BlockRepository blockRepository;

    /**
     * Создает новый блок и сохраняет его.
     *
     * @param block Объект блока, который нужно создать.
     * @return Созданный блок.
     */
    @Transactional
    public Block create(Block block) {
       return blockRepository.save(block);
    }

    /**
     * Возвращает список всех блоков.
     *
     * @return Список всех блоков.
     */
    public List<Block> findAll() {
        return blockRepository.findAll();
    }

    /**
     * Находит блок по его идентификатору.
     *
     * @param id Идентификатор блока.
     * @return Опциональный объект блока (может быть пустым, если блок не найден).
     */
    public Optional<Block> findById(int id) {
        return blockRepository.findById(id);
    }

    /**
     * Обновляет описание и имя блока по его идентификатору.
     *
     * @param id   Идентификатор блока.
     * @param desc Новое описание блока.
     * @param name Новое имя блока.
     */
    @Transactional
    public void update(int id, String desc, String name) {
        blockRepository.updateBy(id, desc, name);
    }

    /**
     * Удаляет блок по его идентификатору.
     *
     * @param id Идентификатор блока, который нужно удалить.
     */
    @Transactional
    public void delete(int id) {
        blockRepository.deleteById(id);
    }

    /**
     * Возвращает список уроков, принадлежащих блоку с заданным идентификатором.
     *
     * @param id Идентификатор блока.
     * @return Список уроков, принадлежащих блоку.
     */
    public List<Lesson> findAllLessonByBlockId(int id) {
        return blockRepository.findAllLessonsByBLockId(id);
    }
}

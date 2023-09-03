package ru.lopatuxin.scola.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lopatuxin.scola.models.Block;
import ru.lopatuxin.scola.models.Lesson;
import ru.lopatuxin.scola.repositories.BlockRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BlockService {

    private final BlockRepository blockRepository;

    @Autowired
    public BlockService(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    @Transactional
    public Block create(Block block) {
       return blockRepository.save(block);
    }

    public List<Block> findAll() {
        return blockRepository.findAll();
    }

    public Optional<Block> findById(int id) {
        return blockRepository.findById(id);
    }

    @Transactional
    public void update(int id, String desc, String name) {
        blockRepository.updateBy(id, desc, name);
    }

    @Transactional
    public void delete(int id) {
        blockRepository.deleteById(id);
    }

    public List<Lesson> findAllLessonByBlockId(int id) {
        return blockRepository.findAllLessonsByBLockId(id);
    }
}

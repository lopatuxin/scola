package ru.lopatuxin.scola.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lopatuxin.scola.models.Block;
import ru.lopatuxin.scola.repositories.BlockRepository;

@Service
public class BlockService {

    private final BlockRepository blockRepository;

    @Autowired
    public BlockService(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    @Transactional
    public void create(Block block) {
        blockRepository.save(block);
    }
}

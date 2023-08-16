package ru.lopatuxin.scola.services;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.lopatuxin.scola.models.Block;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class BlockServiceTest {

    private final BlockService blockService;

    @AfterEach
    public void clear() {
        blockService.findAll()
                .forEach(block -> blockService.delete(block.getId()));
    }

    @Test
    void create() {
        Block block = new Block("test", "test");
        Block exp = blockService.create(block);
        assertThat(block).isEqualTo(exp);
    }

    @Test
    void findAll() {
        Block block = new Block("test", "test");
        blockService.create(block);
        assertThat(blockService.findAll().size()).isEqualTo(1);
    }

    @Test
    void findById() {
        Block block = new Block("test", "test");
        Block exp = blockService.create(block);
        assertThat(block).isEqualTo(blockService.findById(exp.getId()).get());
    }

    @Test
    void update() {
        Block block = new Block("test", "test");
        Block exp = blockService.create(block);
        block.setName("test1");
        block.setDescription("test1");
        blockService.update(exp.getId(), block.getDescription(), block.getName());
        assertThat(block).isEqualTo(blockService.findById(exp.getId()).get());
    }

    @Test
    void delete() {
        Block block = new Block("test", "test");
        Block exp = blockService.create(block);
        blockService.delete(exp.getId());
        assertThat(blockService.findAll()).isEmpty();
    }
}
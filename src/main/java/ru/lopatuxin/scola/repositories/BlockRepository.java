package ru.lopatuxin.scola.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lopatuxin.scola.models.Block;

@Repository
public interface BlockRepository extends JpaRepository<Block, Integer> {
}

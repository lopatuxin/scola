package ru.lopatuxin.scola.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lopatuxin.scola.models.Block;
import ru.lopatuxin.scola.models.Lesson;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlockRepository extends JpaRepository<Block, Integer> {

    @Modifying
    @Query("update Block b set b.description = :desc, b.name = :name where b.id = :id")
    void updateBy(@Param("id") int id, @Param("desc") String desc, @Param("name") String name);

    @Query("SELECT l FROM Lesson l WHERE l.block.id = :bid")
    List<Lesson> findAllLessonsByBLockId(@Param("bid") int id);

   /* @Query("select b from Block b left join fetch b.lessons where b.id = :id")
    Optional<Block> findById(int id);*/
}

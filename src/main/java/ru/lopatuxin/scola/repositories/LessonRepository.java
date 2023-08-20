package ru.lopatuxin.scola.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.lopatuxin.scola.models.Lesson;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    @Query("from Lesson les join fetch les.block")
    List<Lesson> findAllWithBlock();
}
